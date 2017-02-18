import {welcome} from './greetings.js'
import {suggestKeyup} from './suggest.js'
import {importFeed} from "./feed";
import {buildPlayer} from "./listen";
import {bindButtonUiActions} from "./buttons";

buildPlayer();
bindButtonUiActions();

// test greeting
welcome();
importFeed();
suggestKeyup();
