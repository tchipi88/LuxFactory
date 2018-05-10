(function () {
    'use strict';

    angular
            .module('app')
            .controller('FournisseurDialogController', FournisseurDialogController);

    FournisseurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal', 'DataUtils', 'entity', 'Fournisseur', 'Employe'];

    function FournisseurDialogController($timeout, $scope, $stateParams, $uibModalInstance, $uibModal, DataUtils, entity, Fournisseur, Employe) {
        var vm = this;

        vm.fournisseur = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.responsables = Employe.query();
        //vm.produitFournisseurs = produitFournisseurs;



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.fournisseur.id !== null) {
                Fournisseur.update(vm.fournisseur, onSaveSuccess, onSaveError);
            } else {
                Fournisseur.save(vm.fournisseur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tkbrApp:fournisseurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }




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
                        vm.fournisseur[fieldName] = base64Data;
                        vm.fournisseur[fieldName + 'ContentType'] = $file.type;
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
                vm.fournisseur[fieldname] = item;
            }, function () {

            });
        };

        vm.editProduitFournisseur = function (entity, index) {
            $uibModal.open({
                templateUrl: 'tpl/entities/produit-fournisseur/produit-fournisseur-dialog.html',
                controller: 'ProduitFournisseurDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function (item) {
                vm.produitFournisseurs[index] = item;
            }, function () {

            });
        };
        vm.delProduitFournisseur = function (entity, index) {
            $uibModal.open({
                templateUrl: 'tpl/entities/produit-fournisseur/produit-fournisseur-delete-dialog.html',
                controller: 'ProduitFournisseurDeleteController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function () {
                vm.produitFournisseurs.splice(index, 1);
            }, function () {

            });
        };
        vm.addProduitFournisseur = function () {
            $uibModal.open({
                templateUrl: 'tpl/entities/produit-fournisseur/produit-fournisseur-dialog.html',
                controller: 'ProduitFournisseurDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            fournisseur: vm.fournisseur
                        };
                    }
                }
            }).result.then(function (item) {
                vm.produitFournisseurs.push(item);
            }, function () {

            });
        };

    }
})();
