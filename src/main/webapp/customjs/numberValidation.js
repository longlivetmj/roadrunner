/**
    * Created by IntelliJ IDEA.
    * User: Udaya-Ehl
    * Date: 1/26/2017
    * Time: 11:31 AM
    * To change this template use File | Settings | File Templates.
    */

function validateFloatKeyPress(el, param) {
    if (el.value != '') {
        var v = parseFloat(el.value);
        el.value = (isNaN(v)) ? '' : v.toFixed(param);
    }
}

function validateIntegerKeyPress(el) {
    if (el.value != '') {
        var v = parseInt(el.value);
        el.value = (isNaN(v)) ? '' : v;
    }
}

function limitIntegerKeyPressValue(el,max) {
    if (el.value != '') {
        var v = parseFloat(el.value);
        el.value = (isNaN(v)) ? '' : v;
        if(v > max){
            el.value = max;
        }
    }
}