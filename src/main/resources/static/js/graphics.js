/**
 * Builds graphic based on provided dates and stocks
 * 
 * @param {Array} dates Format: [{"stockName1":[value1, value2, ...]},{"stockName2": [value1, value2, ...]},...]
 * @param {Object} stocks Format: ["date1","date2",...]
 */
function cargarIndices(dates, stocks) {
    data = [];

    // TODO: Acabar esto
    // Construct header
    let head = [];
    Object.keys(stocks).forEach((stockName) =>{
        head.push(stockName);
    });
    head.push('date');
    data.push(head);



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

    c3.generate({
        data: {
            x: 'date',
            rows: [
                ['TSLA', 'NVDA', 'AMZN', 'date'],
                [1000, 156, 432, '2010-10-4'],
                [123, 156, 432, '2010-10-5'],
                [123, 156, 432, '2010-10-6'],
                [123, 156, 432, '2010-10-7'],
            ]
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