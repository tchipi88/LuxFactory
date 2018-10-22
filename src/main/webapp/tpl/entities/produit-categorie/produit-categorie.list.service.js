(function() {
    'use strict';

    angular
        .module('app')
        .factory('ProduitCategorieList', ProduitCategorieList);

    ProduitCategorieList.$inject = ['$resource'];

    function ProduitCategorieList($resource) {
        var resourceUrl =  'api/list/produitcategories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
