(function() {
    'use strict';

    angular
        .module('app')
        .controller('ListeFacturesDeleteController',ListeFacturesDeleteController);

    ListeFacturesDeleteController.$inject = ['$uibModalInstance', 'entity', 'ListeFactures'];

    function ListeFacturesDeleteController($uibModalInstance, entity, ListeFactures) {
        var vm = this;

        vm.listeFactures = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ListeFactures.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
