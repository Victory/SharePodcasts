import {welcome} from './greetings.js'
import {suggestKeyup} from './suggest.js'
import {importFeed} from "./feed";
import {buildPlayer} from "./listen";

buildPlayer();

// test greeting
welcome();
importFeed();
suggestKeyup();
