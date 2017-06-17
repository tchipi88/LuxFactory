(function() {
    'use strict';

    angular
        .module('app')
        .controller('[(${entity})]DeleteController',[(${entity})]DeleteController);

    [(${entity})]DeleteController.$inject = ['$uibModalInstance', 'entity', '[(${entity})]'];

    function [(${entity})]DeleteController($uibModalInstance, entity, [(${entity})]) {
        var vm = this;

        vm.[(${entity_var})] = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            [(${entity})].delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
