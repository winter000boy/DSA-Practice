var createCounter = function(n) {
    
    return function() {
        const counter = n;
        return counter++;
    };
};
