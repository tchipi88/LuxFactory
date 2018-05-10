(function () {
    'use strict';

    angular
            .module('app')
            .controller('EmployeDialogController', EmployeDialogController);

    EmployeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal', 'DataUtils', 'entity', 'Employe', 'EmployeFonction'];

    function EmployeDialogController($timeout, $scope, $stateParams, $uibModalInstance, $uibModal, DataUtils, entity, Employe, EmployeFonction) {
        var vm = this;

        vm.employe = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.fonctions = EmployeFonction.query();



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.employe.id !== null) {
                Employe.update(vm.employe, onSaveSuccess, onSaveError);
            } else {
                Employe.save(vm.employe, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tkbrApp:employeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


        vm.datePickerOpenStatus.dateNaissance = false;
        vm.datePickerOpenStatus.dateEntree = false;


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
                        vm.employe[fieldName] = base64Data;
                        vm.employe[fieldName + 'ContentType'] = $file.type;
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
                vm.employe[fieldname] = item;
            }, function () {

            });
        };

    }
})();
