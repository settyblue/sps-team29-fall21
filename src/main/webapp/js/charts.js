google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

/** Fetches bigfoot sightings data and uses it to create a chart. */
function drawChart() {
  fetch('/bigfoot-data').then(response => response.json())
  .then((bigfootSightings) => {
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'Year');
    data.addColumn('number', 'Sightings');
    Object.keys(bigfootSightings).forEach((year) => {
      data.addRow([year, bigfootSightings[year]]);
    });

    const options = {
      'title': 'Bigfoot Sightings',
      'width':600,
      'height':500
    };

    const chart = new google.visualization.LineChart(
        document.getElementById('chart-container'));
    chart.draw(data, options);
  });
}