(function() {
    'use strict';
    angular
        .module('app')
        .factory('ReleveEngin', ReleveEngin);

    ReleveEngin.$inject = ['$resource','DateUtils'];

    function ReleveEngin ($resource,DateUtils) {
        var resourceUrl =  'api/releve-engins/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                          data.dateReleve =DateUtils.convertLocalDateFromServer(data.dateReleve);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateReleve =DateUtils.convertLocalDateToServer(copy.dateReleve);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateReleve =DateUtils.convertLocalDateToServer(copy.dateReleve);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
