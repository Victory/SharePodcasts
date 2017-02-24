import {welcome} from './greetings.js'
import {suggestKeyup} from './suggest.js'
import {importFeed} from "./feed";
import {buildPlayer} from "./listen";
import {bindButtonUiActions} from "./buttons";
import {bindSkipCustomShareLink} from "./createsharelink";

buildPlayer();
bindButtonUiActions();
bindSkipCustomShareLink();

// test greeting
welcome();
importFeed();
suggestKeyup();
