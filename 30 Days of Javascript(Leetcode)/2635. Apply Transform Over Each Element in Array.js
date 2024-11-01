/**
 * @param {number[]} arr
 * @param {Function} fn
 * @return {number[]}
 */
var map = function(arr, fn) {
    const newArr = arr;

    for(i=0; i<arr.length; i++){
        newArr[i] = fn(arr[i],i)
    }
    return newArr;
};


// array.map(currentvalue, index,array  => {return currentvalue*2})
