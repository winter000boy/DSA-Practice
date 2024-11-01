//array.filter(currentValue, index, arr)

/**
 * @param {number[]} arr
 * @param {Function} fn
 * @return {number[]}
 */
var filter = function(arr, fn) {
    let newArr = [];

    for(i=0; i<arr.length; i++){
        if(fn(arr[i], i)){
            newArr.push(arr[i])
        }
    }

    return newArr

};