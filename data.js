const fs = require('fs');

function listForm(int) {
    var result = Math.abs(int).toString().split('').reverse().join('->');
    result += '->\\';
    return result;
}

function info(int) {
    var result = '';
    result += int + '\n';
    result += listForm(int) + '\n';
    result += (int < 0) + '\n';
    result += int.toString().length + (int < 0 ? -1 : 0) + '\n';
    return result;
}

function main() {
    fs.unlinkSync('input.txt');
    fs.unlinkSync('expected.txt');
    var size = 100;
    var parse = [];
    var sum = [];
    var multiply = [];

    for (var i = 0; i < size; i++) {
        parse.push(Math.floor(Math.random() * 2000000) - 1000000);
    }
    for (var i = 0; i < size; i++) {
        sum.push(Math.floor(Math.random() * 2000000) - 1000000);
        sum.push(Math.floor(Math.random() * 2000000) - 1000000);
    }
    for (var i = 0; i < size; i++) {
        multiply.push(Math.floor(Math.random() * 2000000) - 1000000);
        multiply.push(Math.floor(Math.random() * 2000000) - 1000000);
    }

    for (var i = 0; i < size; i++) {
        var val = parse[i];
        fs.appendFileSync('input.txt', 'p\n' + val + '\n');
        fs.appendFileSync('expected.txt', info(val));
    }
    for (var i = 0; i < size*2; i += 2) {
        var val1 = sum[i];
        var val2 = sum[i+1];
        fs.appendFileSync('input.txt', 'a\n' + val1 + '\n' + val2 + '\n');
        fs.appendFileSync('expected.txt', info(val1 + val2));
    }
    for (var i = 0; i < size*2; i += 2) {
        var val1 = multiply[i];
        var val2 = multiply[i+1];
        fs.appendFileSync('input.txt', 'm\n' + val1 + '\n' + val2 + '\n');
        fs.appendFileSync('expected.txt', info(val1 * val2));
    }
    fs.appendFileSync('input.txt', 'q\n');
}

main();