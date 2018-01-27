function loopWithVarAssignemnt(max) {
    var x = max;
    while(x > 10 || x < 10){
        console.log("x = "+x);
    }
}
function whileConditional(max) {

    while(max > 10 || max < 10){
        console.log("max = "+max);
    }
}

function whileLoop(max) {
    while(max){
        console.log("max = "+max);
    }
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
}

function forInLoop(object1) {
    for (var property1 in object1) {
        string1 = string1 + object1[property1];
    }
    console(string1);
}


function simpleLoop(max){
    for(var i = 0; i< max; i++){
        console.log("");
    }
}

function doubleLoop(max) {
    for(var i = 0; i< max; i++){
        for(var j=0; j< i; j++){
            console.log("");
        }
    }
}

function tripleLoop(max) {
    for(var i = 0; i< max; i++){
        for(var j=0; j< i; j++){
            for(var k=0; k< j; k++){
                console.log("");
            }
        }
    }
}

function fourLoop(max) {
    for(var i = 0; i< max; i++){
        for(var j=0; j< i; j++){
            for(var k=0; k< j; k++){
                for(var l=0; l<k; l++){
                    console.log("");
                }
            }
        }
    }
}

function timeoutLoop() {

    for(var i = 0; i< 1000; i++){
        setTimeout(function () {
            console.log("---");
        },3000)
    }
}

///////////////////////////////////////////////////////////////////////


/*var process = require('process');

function whileConditional(max) {
    var start = process.hrtime();
    while(max > 10 || max < 100){
        console.log("");
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}

function whileLoop(max) {
    var start = process.hrtime();
    while(max){
        console.log("");
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}


function forLoopIncr(max){
    var start = process.hrtime();
    for(var i=0; i< max;i++){
        console.log("");
        i = 1;
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}

function forLoopDecr(max){
    var start = process.hrtime();
    for(var i=max; i< 0;i--){
        console.log("");
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}



function forInLoop(object1) {
    var start = process.hrtime();
    for (var property1 in object1) {
        string1 = string1 + object1[property1];
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}


function simpleLoop(max){

    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< max; i++){
        console.log("");
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}


function doubleLoop(max) {
    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< max; i++){
        for(var j=0; j< i; j++){
            console.log("");
        }
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}


function tripleLoop(max) {
    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< max; i++){
        for(var j=0; j< i; j++){
            for(var k=0; k< j; k++){
                console.log("");
            }
        }
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}



function fourLoop(max) {
    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< max; i++){
        for(var j=0; j< i; j++){
            for(var k=0; k< j; k++){
                for(var l=0; l<k; l++){
                    console.log("");
                }
            }
        }
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}



function timeoutLoop() {
    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< 1000; i++){
        setTimeout(function () {
            console.log("---");
        },3000)
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}*/
/*

whileLoop(1000);
forLoopIncr(10000);
forLoopDecr(10000);

whileConditional(11);
var object1 = {a: 1, b: 2, c: 3};
forInLoop(object1);

simpleLoop(1000000);
doubleLoop(1000);
tripleLoop(200);
fourLoop(100);
timeoutLoop();*/
