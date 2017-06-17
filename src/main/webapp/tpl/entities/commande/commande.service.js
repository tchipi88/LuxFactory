(function () {
    'use strict';
    angular
            .module('app')
            .factory('Commande', Commande);

    Commande.$inject = ['$resource', 'DateUtils'];

    function Commande($resource, DateUtils) {
        var resourceUrl = 'api/commandes/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateEmission = DateUtils.convertLocalDateFromServer(data.dateEmission);
                        data.dateLivraison = DateUtils.convertLocalDateFromServer(data.dateLivraison);
                        data.dateEcheance = DateUtils.convertLocalDateFromServer(data.dateEcheance);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateEmission = DateUtils.convertLocalDateToServer(copy.dateEmission);
                    copy.dateLivraison = DateUtils.convertLocalDateToServer(copy.dateLivraison);
                    copy.dateEcheance = DateUtils.convertLocalDateToServer(copy.dateEcheance);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateEmission = DateUtils.convertLocalDateToServer(copy.dateEmission);
                    copy.dateLivraison = DateUtils.convertLocalDateToServer(copy.dateLivraison);
                    copy.dateEcheance = DateUtils.convertLocalDateToServer(copy.dateEcheance);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
