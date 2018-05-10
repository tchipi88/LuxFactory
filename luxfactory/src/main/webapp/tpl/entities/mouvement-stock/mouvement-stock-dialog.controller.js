(function () {
    'use strict';

    angular
            .module('app')
            .controller('MouvementStockDialogController', MouvementStockDialogController);

    MouvementStockDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal', 'DataUtils', 'entity', 'MouvementStock', 'Entrepot', 'Produit'];

    function MouvementStockDialogController($timeout, $scope, $stateParams, $uibModalInstance, $uibModal, DataUtils, entity, MouvementStock, Entrepot, Produit) {
        var vm = this;

        vm.mouvementStock = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.entrepotdeparts = Entrepot.query();
        vm.entrepotdestinations = Entrepot.query();
        vm.produits = Produit.query();



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.mouvementStock.id !== null) {
                MouvementStock.update(vm.mouvementStock, onSaveSuccess, onSaveError);
            } else {
                MouvementStock.save(vm.mouvementStock, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tkbrApp:mouvementStockUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

         vm.datePickerOpenStatus.dateTransaction = false;


        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.setMimage = function ($file, fieldName) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        vm.mouvementStock[fieldName] = base64Data;
                        vm.mouvementStock[fieldName + 'ContentType'] = $file.type;
                    });
                });
            }
        };

        vm.zoomColumn = function (lookupCtrl, lookupTemplate, fieldname, entity) {
            $uibModal.open({
                templateUrl: 'tpl/entities/' + lookupTemplate + '/' + lookupTemplate + '-dialog.html',
                controller: lookupCtrl + 'DialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function (item) {
                vm.mouvementStock[fieldname] = item;
            }, function () {

            });
        };

    }
})();
