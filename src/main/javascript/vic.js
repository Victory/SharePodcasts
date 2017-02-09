import {vPromise} from './vPromise.js'

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

module.exports.showError = (msg) => {
    alert(msg);
};

module.exports.vLog = log;
