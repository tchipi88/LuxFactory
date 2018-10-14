(function() {
    'use strict';

    angular
        .module('app')
        .factory('EntrepotList', EntrepotList);

    EntrepotList.$inject = ['$resource'];

    function EntrepotList($resource) {
        var resourceUrl =  'api/list/entrepots';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
