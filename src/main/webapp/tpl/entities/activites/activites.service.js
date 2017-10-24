(function() {
    'use strict';
    angular
        .module('app')
        .factory('Activites', Activites);

    Activites.$inject = ['$resource','DateUtils'];

    function Activites ($resource,DateUtils) {
        var resourceUrl =  'api/activitess/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateDebut =DateUtils.convertLocalDateFromServer(data.dateDebut);
                        data.dateFin =DateUtils.convertLocalDateFromServer(data.dateFin);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateDebut =DateUtils.convertLocalDateToServer(copy.dateDebut);
                     copy.dateFin =DateUtils.convertLocalDateToServer(copy.dateFin);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateDebut =DateUtils.convertLocalDateToServer(copy.dateDebut);
                    copy.dateFin =DateUtils.convertLocalDateToServer(copy.dateFin);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
