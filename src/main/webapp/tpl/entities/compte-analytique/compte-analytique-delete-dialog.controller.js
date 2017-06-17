(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteAnalytiqueDeleteController',CompteAnalytiqueDeleteController);

    CompteAnalytiqueDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompteAnalytique'];

    function CompteAnalytiqueDeleteController($uibModalInstance, entity, CompteAnalytique) {
        var vm = this;

        vm.compteAnalytique = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CompteAnalytique.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
