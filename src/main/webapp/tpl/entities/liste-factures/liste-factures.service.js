(function() {
    'use strict';
    angular
        .module('app')
        .factory('ListeFactures', ListeFactures);

    ListeFactures.$inject = ['$resource','DateUtils'];

    function ListeFactures ($resource,DateUtils) {
        var resourceUrl =  'api/liste-facturess/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                          data.dateEmission =DateUtils.convertLocalDateFromServer(data.dateEmission);
 data.dateEcheance =DateUtils.convertLocalDateFromServer(data.dateEcheance);
 data.dateLivraison =DateUtils.convertLocalDateFromServer(data.dateLivraison);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateEmission =DateUtils.convertLocalDateToServer(copy.dateEmission);
 copy.dateEcheance =DateUtils.convertLocalDateToServer(copy.dateEcheance);
 copy.dateLivraison =DateUtils.convertLocalDateToServer(copy.dateLivraison);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateEmission =DateUtils.convertLocalDateToServer(copy.dateEmission);
 copy.dateEcheance =DateUtils.convertLocalDateToServer(copy.dateEcheance);
 copy.dateLivraison =DateUtils.convertLocalDateToServer(copy.dateLivraison);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
