(function () {
    'use strict';
    angular
            .module('app')
            .factory('Employe', Employe);

    Employe.$inject = ['$resource', 'DateUtils'];

    function Employe($resource, DateUtils) {
        var resourceUrl = 'api/employes/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateNaissance = DateUtils.convertLocalDateFromServer(data.dateNaissance);
                        data.dateEntree = DateUtils.convertLocalDateFromServer(data.dateEntree);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateNaissance = DateUtils.convertLocalDateToServer(copy.dateNaissance);
                    copy.dateEntree = DateUtils.convertLocalDateToServer(copy.dateEntree);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateNaissance = DateUtils.convertLocalDateToServer(copy.dateNaissance);
                    copy.dateEntree = DateUtils.convertLocalDateToServer(copy.dateEntree);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
