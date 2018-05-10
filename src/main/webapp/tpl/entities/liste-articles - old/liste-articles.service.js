(function() {
    'use strict';
    angular
        .module('app')
        .factory('ListeArticles', ListeArticles);

    ListeArticles.$inject = ['$resource','DateUtils'];

    function ListeArticles ($resource,DateUtils) {
        var resourceUrl =  'api/liste-articless/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateTransaction =DateUtils.convertLocalDateFromServer(data.dateTransaction);
                        data.lastOut =DateUtils.convertLocalDateFromServer(data.lastOut);
                        data.lastIn =DateUtils.convertLocalDateFromServer(data.lastIn);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateTransaction =DateUtils.convertLocalDateToServer(copy.dateTransaction);
                     copy.lastOut =DateUtils.convertLocalDateToServer(copy.lastOut);
                     copy.lastIn =DateUtils.convertLocalDateToServer(copy.lastIn);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateTransaction =DateUtils.convertLocalDateToServer(copy.dateTransaction);
                     copy.lastOut =DateUtils.convertLocalDateToServer(copy.lastOut);
                     copy.lastIn =DateUtils.convertLocalDateToServer(copy.lastIn);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
