# CurrencyConverter
 This app uses the Kraken exchange API to get information about cryptocurrency pair quotes and also to get the history of quotes.

For convenience, information about the quotes of the two most popular currencies Bitcoin and Etherium is displayed on the main page.

Information about the quote is taken at the beginning of the day. You are asked to select the currency pair you are interested in or choose from predefined values, enter the desired amount to convert one to another, and click the button 'Convert' to get the result.

Based on the information received via the API, you can see the quote at the beginning of the day, as well as the conversion result and the chart with the latest exchange rate changes. You can also see the last requested calculation.

There is a hidden admin account available at the link /admin. There you can see the number of completed requests and the results of the last 20 requests. You can clear the repository using an HTTP request.

The app has its own API at the link /api/new that works on a POST request. In the request body, pass a JSON object containing the name of the pair and the amount to be exchanged:

{ "pairNameDTO" : "ADAETH : from ADA to ETH", "quantityDTO" : "100" }

As a result, we will get a JSON object containing the name of the pair, the date and time of the request, the names of the source and destination currency, the amount to be converted, the exchange rate, and the total cost, like this:

{ "id": "ADAETH", "date": "2020-11-16 14:56:58", "price": "0.000223200", "quantity": "100", "sourceCurrency": "ADA", "requiredCurrency": "ETH", "amount": "0.02232" }

Work is underway to add new features to the app.
