import {suggestKeyup} from './suggest.js'
import {importFeed} from "./feed";
import {buildPlayer} from "./listen";
import {bindButtonUiActions} from "./buttons";
import {bindSkipCustomShareLink} from "./createsharelink";
import {bindBodyBox} from "./bodybox";

bindBodyBox();
buildPlayer();
bindSkipCustomShareLink();

bindButtonUiActions();
importFeed();
suggestKeyup();
