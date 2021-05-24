/**
 * Builds graphic based on provided dates and stocks
 * 
 * stocks format: 
 *  {
 *      {stockName1:[
 *          {date1:value1}, 
 *          {date2:value2},
 *          ...
 *      ]}, 
 *      {stockName2:[
 *          {date1:value1}, 
 *          {date2:value2},
 *          ...
 *      ]},
 *      ...
 *  }
 */
function cargarIndices(stocks) {
    data = [];

    // Construct header
    let head = [];
    Object.keys(stocks).forEach((stockName) => {
        head.push(stockName);
    });
    head.push('date');
    data.push(head);

    // Construct values
    numStocks = Object.values(stocks).length
    numValues = Object.values(Object.values(stocks)[0]).length
    for (let i = 0; i < numValues; i++) {
        let newSetOfValues = [];

        for (let j = 0; j < numStocks; j++) {
            newSetOfValues.push(Object.values(Object.values(stocks)[j])[i]);
        }
        newSetOfValues.push(Object.keys(Object.values(stocks)[0])[i]);
        data.push(newSetOfValues);
    }

    /**
     * Format:
     * data = [
     *   ['TSLA', 'NVDA', 'AMZN', 'date'],
     *   [123, 156, 432, '2010-10-4'],
     *   [123, 156, 432, '2010-10-5'],
     *   [123, 156, 432, '2010-10-6'],
     *   [123, 156, 432, '2010-10-7']
     * ]
     */

    console.log(data)

    c3.generate({
        data: {
            x: 'date',
            rows: data
        },
        axis: {
            x: {
                type: 'timeseries',
                tick: {
                    format: '%Y-%m-%d'
                }
            }
        }
    })
}