angular.module('app.directives', [])
    // Register the 'myCurrentTime' directive factory method.
    // We inject $timeout and dateFilter service since the factory method is DI.
    .directive('visualdata', function(Rest) {
        // return the directive link function. (compile function not needed)
        return function(scope, element, attrs,controller) {


            require([
                // Require the basic chart class
                "dojox/charting/Chart",

                // Require the theme of our choosing
                "dojox/charting/themes/Claro",

                // Charting plugins:

                // 	We want to plot a Pie chart
                "dojox/charting/plot2d/Pie",

                // Retrieve the Legend, Tooltip, and MoveSlice classes
                "dojox/charting/action2d/Tooltip",
                "dojox/charting/action2d/MoveSlice",

                //	We want to use Markers
                "dojox/charting/plot2d/Markers",

                //	We'll use default x/y axes
                "dojox/charting/axis2d/Default",

                // Wait until the DOM is ready
                "dojo/domReady!"
            ], function(Chart, theme, Pie, Tooltip, MoveSlice) {

                // Define the data
                // JH: We should define a variable call pieChartData[]
                //var chartData = [10000,9200,11811,12000,7662,13887,14200,12222,12000,10009,11288,12099];
                var chartData = [10000,9200,811,2099];

                // Create the chart within it's "holding" node
                // "pieChartNode" is the holding node div created
                var chart = new Chart("pieChartNode");

                // Set the theme
                chart.setTheme(theme);

                // Add the only/default plot
                chart.addPlot("default", {
                    type: Pie,
                    markers: true,

                    radius:120,
                    font: "normal bold 11pt Tahoma",
                    fontColor: "white",
                    labelOffset: 50,
                    animate:{duration: 1000}
                });

                // Add axes
                chart.addAxis("x");
                chart.addAxis("y", { min: 5000, max: 30000, vertical: true, fixLower: "major", fixUpper: "major" });


                // Create the tooltip
                var tip = new Tooltip(chart,"default");

                // Create the slice mover
                var mag = new MoveSlice(chart,"default");



                scope.$on('handleAuthenticate', function() {
                    document.location.reload();
                });





                Rest.getVisulizationData(function(data){
                    if(data.monthly_left_amount == null) {
                        return;
                    }

                    var monthly_left_amount = data.monthly_left_amount;
                    var monthly_spent_amount = data.monthly_spent_amount;
                    chart.addSeries("Monthly Budget", [
                        {y: monthly_left_amount, text: "$" + monthly_left_amount + " Left",   stroke: "white", tooltip: "$" + monthly_left_amount + " Left",fill: "green"},
                        {y: monthly_spent_amount, text: "$" + monthly_spent_amount + " Spent", stroke: "white", tooltip: "$" + monthly_spent_amount + " Spent",fill: "red"}

                    ]);
                    chart.render();

                });


            });

            require([
                // Require the basic chart class
                "dojox/charting/Chart",



                // Require the theme of our choosing
                "dojox/charting/themes/MiamiNice",

                // Charting plugins:

                // 	We want to plot Columns
                "dojox/charting/plot2d/Columns",

                // Require the highlighter
                "dojox/charting/action2d/Highlight",

                //	We want to use Markers
                "dojox/charting/plot2d/Markers",

                //	We'll use default x/y axes
                "dojox/charting/axis2d/Default",

                // Wait until the DOM is ready
                "dojo/domReady!"
            ], function(Chart, theme, Columns, Highlight) {

                // Define the data
                // JH: We should define a variable call barChartData[]
                var chartData = [1500,1500,1500,1500,1500,1500,1500];
                //   var chartData = [100,9200,11811,12000,7662,13887,14200,12222,12000,10009,11288,12099];

                // Create the chart within it's "holding" node
                // "barChartNode" is the holding node div created
                var chart = new Chart("barChartNode");

                // Set the theme
                chart.setTheme(theme);

                // Add the only/default plot
                chart.addPlot("default", {
                    type: Columns,
                    markers: true,
                    gap: 40,
                    animate:{duration: 1000}

                });

                // Add axes




                // Add the series of data
                chart.addSeries("Monthly Sales",chartData,{ fill: "green"});

                // Highlight!
                new Highlight(chart,"default");

                // Render the chart!
                // chart.render();

                Rest.getVisulizationData(function(data){

                    var barchartPurchaseAmounts = data.daily_barchart_purchase_amounts;
                    var daily_barchart_dates = data.daily_barchart_dates;
                    var datainty = [];
                    var dataintx = [];
                    for (var i in barchartPurchaseAmounts) {
                        datainty[i] = { x: i , y : parseInt(barchartPurchaseAmounts[i], 10) }
                        dataintx[i] = {value: i, text:daily_barchart_dates[i] }

                    }


                    chart.addAxis("x",{
                        labels: dataintx,
                        rotation: 25,
                        majorLabels: true, majorTicks: true, majorTick: {length:7},
                        minorLabels: false, minorTicks:false, minorTick:{length:6},
                        font: "normal normal bold 12pt Arial"
                    });

                    var myLabelFunc = function(text, value, precision){
                        return "$" + text;
                    };

                    chart.addAxis("y", { labelFunc: myLabelFunc , vertical: true, fixUpper: "major", includeZero: true, font: "normal normal bold 12pt Arial"



                    });
                    chart.addSeries("Monthly Sales",datainty, {fill: "red"});


                    chart.render();

                });

            });





        }
    }).directive('gmapdata', function(Rest) {

        return function(scope, element, attrs,controller) {


            Rest.getAllCafes(function(datas) {

                var cafes = datas.cafes;

                var myLatlng = new google.maps.LatLng(37.349588,-121.93747);
                var mapOptions = {
                    center: myLatlng,
                    zoom: 14,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };

                var dd = document.getElementById('map_canvas');
                var map = new google.maps.Map(
                    document.getElementById('map_canvas'), mapOptions);


                for(var i = 0; i < cafes.length; i++) {
                    var cafe = cafes[i];
                    var latitude = cafe.latitude;
                    var longitude = cafe.longitude;
                    var image = cafe.filename;
                    var name = cafe.name;
                    var id = cafe.id;




                    var infowindow = new google.maps.InfoWindow();


                    var marker = new google.maps.Marker({
                        position: new google.maps.LatLng(latitude, longitude),
                        map: map,
                        title: name
                    });

                    google.maps.event.addListener(marker, 'click', (function(marker, i) {
                        return function() {


                            var contentString = '<div id="markerContent">'+
                                '<div id="cafe'+ cafes[i].id + '">'+
                                '</div>'+
                                '<h1 id="firstHeading" class="firstHeading">' + cafes[i].name + '</h1>'+
                                '<div id="bodyContent">'+
                                '<img border="0" src="' + cafes[i].filename + '" alt="' + cafes[i].id + '" width="180" height="130">' +
//                                '<p><b>Address:</b> TBD<br>'+
//                                '<b>Review:</b> <a href="review.html">Review Count</a><br></p>' +
                                '<p><a href="index.html#/cafedetail/' + cafes[i].id + '">'+
                                '<b>Order Here</b></a> '+
                                '</div>'+
                                '</div>';
                            infowindow.setContent(contentString);
                            infowindow.open(map, marker);
                        }
                    })(marker, i));

                }


            });






        }









    });
