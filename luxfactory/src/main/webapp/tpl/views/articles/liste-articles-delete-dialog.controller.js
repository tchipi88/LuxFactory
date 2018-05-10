(function() {
    'use strict';

    angular
        .module('app')
        .controller('ListeArticlesDeleteController',ListeArticlesDeleteController);

    ListeArticlesDeleteController.$inject = ['$uibModalInstance', 'entity', 'ListeArticles'];

    function ListeArticlesDeleteController($uibModalInstance, entity, ListeArticles) {
        var vm = this;

        vm.listeArticles = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ListeArticles.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
