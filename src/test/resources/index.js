/*UTF8.encode = function (s) {
    var u = [];
    for (var i = 0; i < s.length; ++i) {
        var c = s.charCodeAt(i);
        if (c < 0x80) {
            u.push(c);
        } else if (c < 0x800) {
            u.push(0xC0 | c >> 6);
            u.push(0x80 | 63 & c);
        } else if (c < 0x10000) {
            u.push(0xE0 | c >> 12);
            u.push(0x80 | 63 & c >> 6);
            u.push(0x80 | 63 & c);
        } else {
            u.push(0xF0 | c >> 18);
            u.push(0x80 | 63 & c >> 12);
            u.push(0x80 | 63 & c >> 6);
            u.push(0x80 | 63 & c);
        }
    }
    return u;
};
function isSafe(userAgent) {
    var consecutive = 0
        , code = 0;

    for (var i = 0; i < userAgent.length; i++) {
        code = userAgent.charCodeAt(i);
        // numbers between 0 and 9, letters between a and z
        if ((code >= 48 && code <= 57) || (code >= 97 && code <= 122)) {
            consecutive++;
        } else {
            consecutive = 0;
        }

        if (consecutive >= 100) {
            return false;
        }
    }

    return true
}

function functionCall (max) {
    var a = max.length;
    for(var i = 0; i < a ; i++){
        console.log(a);
    }
}
function arrayLength(array){
    for(var i = 0; i<array.length; i++){
    }
}

function loopWithVarAssignemnt(max, min) {

    var y = min-10;
    var x = y;


    for (var y = 0; y > min; x++) {
        // for (var x = 0; ( min || bar ) && !( min && bar ); x++) {
        for (var a = 0; 0 || 1; z++) {
            for (var a = 0; 0 && 2; z++) {
                console.log("a" + a)
            }
        }
    }
}

function whileConditional(max) {

    while (max > 10 || max < 10) {
        console.log("max = " + max);
    }
}

function whileLoop(max) {
    while (true) {
        while (x) {
            while (max%10) {
                console.log("max = " + max);
            }

        }
    }
}

function forInLoop(object1) {
    for (var property1 in list) {
        for(var p in object1){
            string1 = string1 + object1[property1];
        }

    }
    console(string1);
}


function forLoopIncr(max){
    for(var i=0; i< max;i++){
        console.log("i = "+i);
    }
}

function forLoopDecr(max){
    for(var i=max; i< 0;i--){
        console.log("i = "+i);
        i = 1;
    }
}*/

function forInLoop(object1) {
    for (var property1 in object1) {
        string1 = string1 + object1[property1];
    }
    console(string1);
}


function simpleLoop(max){
    for(var i = 0; i< max; i++)
        console.log("");
}

function doubleLoop(max) {
    for(var i = 0; i< max; i++)
        for(var j=0; j< i; j++)
            console.log("");
}
function tripleLoop(max) {
    for (var i = 0; i < max; i++)
        for (var j = 0; j < i; j++)
            for (var k = 0; k < j; k++)
                console.log("");
}

function fourLoop(max) {
    for (var i = 0; i < max; i++)
        for (var j = 0; j < max; j++)
            for (var k = 0; k < max; k++)
                for (var l = 0; l < k; l++)
                    console.log("");
}