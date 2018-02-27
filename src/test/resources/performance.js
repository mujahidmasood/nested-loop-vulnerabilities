var process = require('process');
function tripleLoop(max) {
    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< max; i++){
        for(var j=0; j< max; j++){
            for(var k=0; k< max; k++){
                var c = 10 + 5;
            }
        }
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}

tripleLoop(Math.pow(10,3.2));

/*function simpleLoop(max){

    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< max; i++){
        var c = 10 + 5;
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}

simpleLoop(Math.pow(10,10));*/


/*
function doubleLoop(max) {
    var start = process.hrtime();
    var precision = 3;
    for(var i = 0; i< max; i++){
        for(var j=0; j< i; j++){
            var c = 10 + 5;
        }
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}

doubleLoop(Math.pow(10,5));

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

tripleLoop(200);

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

fourLoop(100);

function whileConditional(max) {
    var start = process.hrtime();
    while(max > 10 || max < 100){
        console.log("");
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}

whileConditional(11);

function whileLoop(max) {
    var start = process.hrtime();
    while(max){
        console.log("");
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}
whileLoop(1000);

function forLoopIncr(max){
    var start = process.hrtime();
    for(var i=0; i< max;i++){
        console.log("");
        i = 1;
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}
forLoopIncr(10000);

function forLoopDecr(max){
    var start = process.hrtime();
    for(var i=max; i< 0;i--){
        console.log("");
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}

forLoopDecr(10000);



function forInLoop(object1) {
    var start = process.hrtime();
    for (var property1 in object1) {
        string1 = string1 + object1[property1];
    }

    var elapsed = process.hrtime(start)[1] / 1000000;
    console.log(process.hrtime(start)[0] + " s, " + elapsed.toFixed(precision) + " ms - " );
}
var object1 = {a: 1, b: 2, c: 3};
forInLoop(object1);

*/
