//array.reduce(initialValue, callback(accumulator, currentValue, currentIndex, array), initialValue)

/**
 * @param {number[]} nums
 * @param {Function} fn
 * @param {number} init
 * @return {number}
 */
var reduce = function(nums, fn, init) {
    let total = init;

    for(i=0; i<nums.length; i++){
        total = fn(total , nums[i]);
    }
    return total;
};