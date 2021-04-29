function cargarIndices(indices) {

    // TODO
    let dates = ['x', '2013-01-01', '2013-01-02', '2013-01-03', '2013-01-04', '2013-01-05', '2013-01-06'];
    let indexes = [ ['data1', 30, 200, 100, 400, 150, 250], ['data2', 130, 340, 200, 500, 250, 350] ];
    let data = [];
    data.push(dates);
    indexes.forEach(function(element, index, array) {
        data.push(element);
    })

    var chart = c3.generate({
        data: {
            x: 'x',
            //  xFormat: '%Y%m%d', // 'xFormat' can be used as custom format of 'x'
            columns: 
                data
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
    
    setTimeout(function () {
        chart.load({
            columns: [
                ['data3', 400, 500, 450, 700, 600, 500]
            ]
        });
    }, 1000);
}