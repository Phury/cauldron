var app = angular.module('CauldronApp', [ 'ngRoute', 'ngAnimate']);

app.filter('intCurrency', [ '$filter', '$locale',
	function($filter, $locale) {
		return function(amount, currencySymbol) {
			return $filter('currency')(amount/10, currencySymbol);
		};
	} 
]);

app.config(function($routeProvider) {

    $routeProvider

        // home page
        .when('/', {
            templateUrl: 'partials/transaction-list.html',
            controller: 'transactionController'
        })
//
//        // about page
//        .when('/about', {
//            templateUrl: 'page-about.html',
//            controller: 'aboutController'
//        })
//
//        // contact page
//        .when('/contact', {
//            templateUrl: 'page-contact.html',
//            controller: 'contactController'
//        });

});

app.controller('transactionController', ['$scope', '$http', '$location', 
  function($scope, $http, $location) {
    $scope.pageClass = 'page-transaction-list';
    $scope.today = new Date();
    $http.get('/cauldron-rest/api/expenses').
//    $http.get('/data/expenses.json').
    success(function(data, status, headers, config) {
        $scope.transactions = data;
        //var clientId = $location.path() ? $location.path().replace("/", "") : data[0].id;
        //$scope.currClient = _.find($scope.clients,function(rw){ return rw.id == clientId });
    }).
    error(function(data, status, headers, config) {
    });
    
    $http.get('/cauldron-rest/api/accounts').
    success(function(data, status, headers, config) {
        $scope.accounts = data;
    }).
    error(function(data, status, headers, config) {
    });
  }
]);

app.controller('addTransactionController', ['$scope', '$http', '$filter', 
  function($scope, $http, $filter) {
    $scope.save = function() {
      var jsonModel = {
       'transactionType': $scope.trxType,
	   'account': $scope.trxAccount,
	   'label': $scope.trxLabel,
	   'amount': $scope.trxAmount,
	   'categories': $scope.trxCategories,
	   'entryDate': $filter('date')($scope.trxEntryDate, 'dd-MM-yyyy')
      };
      $http.post('/cauldron-rest/api/transaction', jsonModel).
      success(function(data, status, headers, config) {
          $scope.transactions = data;
          //var clientId = $location.path() ? $location.path().replace("/", "") : data[0].id;
          //$scope.currClient = _.find($scope.clients,function(rw){ return rw.id == clientId });
      }).
      error(function(data, status, headers, config) {
      });
    }
  }
]);