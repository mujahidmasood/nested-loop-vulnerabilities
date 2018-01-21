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
        i = 1;
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


var process = require('process');
function simpleLoop(){

    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< 1000000; i++){
        console.log("");
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}

function doubleLoop() {
    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< 1000; i++){
        for(var j=0; j< i; j++){
            console.log("");
        }
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}

function tripleLoop() {
    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< 200; i++){
        for(var j=0; j< i; j++){
            for(var k=0; k< j; k++){
                console.log("");
            }
        }
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}

function fourLoop() {
    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< 100; i++){
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
}


//timeoutLoop();