(function () {
  'use strict';

  var isObject = function (obj) {
    return typeof obj === "object";
  };

  var isFunction = function (func) {
    return typeof func === "function";
  };

  var states = {
    PENDING: 0,
    FULFILLED: 1,
    REJECTED: 2
  };

  var u = undefined;

  function asap(cb) {
    setTimeout(cb, 1);
  }

  var vPromise = function (fn) {
    this.value = undefined;
    this._state = states.PENDING;
    this.chain = [];
    var prms = this;

    try {
      fn(function (val) {
        prms.resolve(val);
      }, function (reason) {
        prms.reject(reason);
      });
    } catch (exc) {
      prms.reject(exc);
    }
  };

  vPromise.prototype.then = function (onFulfilled, onRejected) {
    var prms = this;

    return new vPromise(function (resolve, reject) {
      prms.chain.push({
        onFulfilled: onFulfilled,
        resolve: resolve,
        onRejected: onRejected,
        reject: reject
      });

      prms.done();
    });
  };

  vPromise.prototype.done = function () {
    var prms = this;

    asap(function () {
      if (prms._state === states.PENDING) {
        return;
      }

      var chainLength = prms.chain.length;
      if (chainLength == 0) { // nothing to do
        return;
      }

      var ii;
      var next;
      var onFulfilled, resolve, onRejected, reject;
      for (ii = 0; ii < chainLength; ii++) {
        next = prms.chain.shift();

        onFulfilled = next.onFulfilled;
        resolve = next.resolve;
        onRejected = next.onRejected;
        reject = next.reject;
        try {
          if (prms._state === states.FULFILLED) {
            if (isFunction(onFulfilled)) {
              resolve(onFulfilled.call(u, prms.value));
            } else {
              resolve(prms.value);
            }
          }

          if (prms._state === states.REJECTED) {
            if (isFunction(onRejected)) {
              resolve(onRejected.call(u, prms.value));
            } else {
              reject(prms.value);
            }
          }
        } catch (reason) {
          reject(reason);
        }
      }
    });
  };

  vPromise.prototype.resolve = function (x) {
    var prms = this;

    if (prms._state !== states.PENDING) {
      return;
    }

    if (prms === x) {
      throw new TypeError("promise and x can not be same object");
    }
    if (x !== null && (isFunction(x) || isObject(x))) {
      try {
        var then = x.then;
      } catch (exc) {
        prms.reject(exc);
        return;
      }
    }

    if (isFunction(then)) {
      var called = false;
      try {
        then.call(x, function (y) {
          if (called) {
            return;
          }
          called = true;
          prms.resolve(y);
        }, function (r) {
          if (called) {
            return;
          }
          called = true;
          prms.reject(r);
        });
        return;
      } catch (reason) {
        if (called) {
          return;
        }
        called = true;
        prms.reject(reason);
        return;
      }
    }

    prms._state = states.FULFILLED;
    prms.value = x;
    prms.done();
  };

  vPromise.prototype.reject = function (reason) {
    var prms = this;

    if (prms._state !== states.PENDING) {
      return;
    }

    if (prms._state === states.PENDING && prms === reason) {
      throw new TypeError();
    }

    prms._state = states.REJECTED;
    prms.value = reason;
    prms.done();
  };

  vPromise.resolve = function (value) {
    return new vPromise(function (resolve) {
      resolve(value);
    });
  };

  vPromise.reject = function (reason) {
    return new vPromise(function (resolve, reject) {
      reject(reason);
    });
  };

  vPromise.all = function (prmes) {
    var ii;
    var numPrms = prmes.length;
    var prmsLeft = numPrms;
    var results = [];
    var aResolve;
    var aReject;

    var resolver = function (index) {
      return function (x) {
        prmsLeft -= 1;
        results[index] = x;
        if (prmsLeft === 0) {
          aResolve(results);
        }
      }
    };

    for (ii = 0; ii < numPrms; ii++) {
      vPromise.resolve(prmes[ii]).then(resolver(ii), function (r) {
        aReject(r);
      });
    }

    return new vPromise(function (resolve, reject) {
      aResolve = resolve;
      aReject = reject;
    });
  };

  vPromise.race = function (prmes) {
    var ii;
    var numPrms = prmes.length;
    var aResolve;
    var aReject;

    var called = false;
    var resolver = function (index) {
      return function (x) {
        if (called) {
          return;
        }
        called = true;
        aResolve(x);
      }
    };

    for (ii = 0; ii < numPrms; ii++) {
      vPromise.resolve(prmes[ii]).then(resolver(ii), function (r) {
        aReject(r);
      });
    }

    return new vPromise(function (resolve, reject) {
      aResolve = resolve;
      aReject = reject;
    });
  };

  if (typeof module != 'undefined') {
    module.exports = vPromise;
    module.exports.vPromise = vPromise;
  }

}());
