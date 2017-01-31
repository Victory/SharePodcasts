let qs = document.querySelector.bind(document);
let log = console.log.bind(window);

let byAttr = (lhs, rhs) => {
    return qs("[" + lhs + "='" + rhs + "']");
};

/** createElement shortcurt */
module.exports.ce = document.createElement.bind(document);

/**
 * Set the text of elm
 * @param elm - element to set text in
 * @param text - text to set
 */
module.exports.setText = (elm, text) => {
    if ("innerText" in elm) {
        elm.innerText = text;
    } else {
        elm.textContent = text;
    }
};

/**
 * Get the text content of the element or text of the target of an event
 * @param elementOrEvent - element or event with .target
 * @return string value
 */
module.exports.getText = (elementOrEvent) => {
    if ("target" in elementOrEvent) {
        if ("innerText" in elementOrEvent.target) {
            return elementOrEvent.target.innerText;
        } else {
            return elementOrEvent.target.textContent;
        }
    } else {
        if ("innerText" in elementOrEvent) {
            return elementOrEvent.innerText;
        } else {
            return elementOrEvent.textContent;
        }
    }
};

/** querySelector shortcut */
module.exports.qs = qs;

/** getElementById shortcut */
module.exports.byid = document.getElementById.bind(document);

/**
 * Get the elements attribute value
 * @param elementOrEvent - element or event with .target property
 */
module.exports.getVal = (elementOrEvent) => {
    if ("target" in elementOrEvent) {
        return elementOrEvent.target.value;
    } else {
        return elementOrEvent.value;
    }
};

/**
 * Uses the namedElements id to select an element with match `for` attribute
 * @param namedElementOrEvent - element with id such as an input or event with target
 */
module.exports.getLabelFor = (namedElementOrEvent) => {
    if ("target" in namedElementOrEvent) {
        return byAttr("for", namedElementOrEvent.target.attributes.id.value);
    } else {
        return byAttr("for", namedElementOrEvent.attributes.id);
    }
};

/* ==== Event Listeners ==== */
/**
 * addEventLitener wrapper
 * @param who - string for query selector or object to bind to
 * @param what - what event to bind to
 * @param callback - call back to trigger
 */
module.exports.listen = (who, what, callback) => {
    let w;
    if (typeof who === "string") {
        w = qs(who);
    } else {
        w = who;
    }
    w.addEventListener(what, callback);
};

/** Just a stub */
module.exports.ajaxPost = (url, options) => {

    var xhr = new XMLHttpRequest();
    xhr.addEventListener("load", t => {
        log(t);
    });
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(options.data);
    return xhr;
};

module.exports.vLog = log;
