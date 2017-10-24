(function () {
    'use strict';

    angular
            .module('app')
            .controller('CommandeDialogController', CommandeDialogController);

    CommandeDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams', '$uibModalInstance', '$uibModal', 'DataUtils', 'entity', 'Commande', 'Fournisseur', 'Employe', 'Client', 'CommandeLigne', 'Reglement'];

    function CommandeDialogController($timeout, $scope, $state, $stateParams, $uibModalInstance, $uibModal, DataUtils, entity, Commande, Fournisseur, Employe, Client, CommandeLigne, Reglement) {
        var vm = this;

        vm.commande = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.superviseurs = Employe.query();
        vm.clients = Client.query();
        vm.fournisseurs = Fournisseur.query();
        vm.commandeLignes = CommandeLigne;
        vm.reglements = Reglement;



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.commande.id !== null) {
                Commande.update(vm.commande, onSaveSuccess, onSaveError);
            } else {
                Commande.save(vm.commande, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tkbrApp:commandeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


        vm.datePickerOpenStatus.dateEmission = false;
        vm.datePickerOpenStatus.dateLivraison = false;
        vm.datePickerOpenStatus.dateEcheance = false;


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
                        vm.commande[fieldName] = base64Data;
                        vm.commande[fieldName + 'ContentType'] = $file.type;
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
                    },
                    produitFournisseurs: function () {
                        return [];
                    }
                }
            }).result.then(function (item) {
                vm.commande[fieldname] = item;
            }, function () {

            });
        };

        vm.editCommandeLigne = function (entity, index) {
            $uibModal.open({
                templateUrl: 'tpl/entities/commande-ligne/commande-ligne-dialog.html',
                controller: 'CommandeLigneDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function (item) {
                vm.commandeLignes[index] = item;
                vm.commande = Commande.get({id: $stateParams.id});
            }, function () {

            });
        };
        vm.delCommandeLigne = function (entity, index) {
            $uibModal.open({
                templateUrl: 'tpl/entities/commande-ligne/commande-ligne-delete-dialog.html',
                controller: 'CommandeLigneDeleteController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function () {
                vm.commandeLignes.splice(index, 1);
                vm.commande = Commande.get({id: $stateParams.id});
            }, function () {

            });
        };
        vm.addCommandeLigne = function () {
            $uibModal.open({
                templateUrl: 'tpl/entities/commande-ligne/commande-ligne-dialog.html',
                controller: 'CommandeLigneDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            commande: vm.commande
                        };
                    }
                }
            }).result.then(function (item) {
                vm.commandeLignes.push(item);
                vm.commande = Commande.get({id: $stateParams.id});
            }, function () {

            });
        };

        vm.addReglement = function () {
            $uibModal.open({
                templateUrl: 'tpl/entities/reglement/reglement-dialog.html',
                controller: 'ReglementDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            commande: vm.commande,
                            mode: 'ESPECES',
                            dateVersement: new Date()
                        };
                    }
                }
            }).result.then(function (item) {
                vm.reglements.push(item);
                vm.commande = Commande.get({id: $stateParams.id});
            }, function () {

            });
        };

    }
})();
