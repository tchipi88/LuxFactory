(function() {
    'use strict';

    angular
        .module('app')
        .factory('CompteAnalytiqueSearch', CompteAnalytiqueSearch);

    CompteAnalytiqueSearch.$inject = ['$resource'];

    function CompteAnalytiqueSearch($resource) {
        var resourceUrl =  'api/_search/compte-analytiques/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
