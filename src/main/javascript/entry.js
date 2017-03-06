import {suggestKeyup} from './suggest'
import {importFeed} from "./feed";
import {buildPlayer} from "./listen";
import {bindButtonUiActions} from "./buttons";
import {bindSkipCustomShareLink} from "./createsharelink";
import {bindBodyBox} from "./bodybox";
import {bindContactForm} from "./contact";

bindBodyBox();
buildPlayer();
bindSkipCustomShareLink();

bindButtonUiActions();
importFeed();
suggestKeyup();

bindContactForm();
