(function() {
    'use strict';

    angular
        .module('app')
        .controller('ListeArticlesDialogController', ListeArticlesDialogController);

    ListeArticlesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'ListeArticles'];

    function ListeArticlesDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, ListeArticles ) {
        var vm = this;

        vm.listeArticles = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        
      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.listeArticles.id !== null) {
                ListeArticles.update(vm.listeArticles, onSaveSuccess, onSaveError);
            } else {
                ListeArticles.save(vm.listeArticles, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:listeArticlesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateTransaction = false;
         vm.datePickerOpenStatus.lastOut = false;
         vm.datePickerOpenStatus.lastIn = false;

        
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
         vm.setMimage = function ($file, fieldName) {
                if ($file && $file.$error === 'pattern') {
                    return;
                }
                if ($file) {
                    DataUtils.toBase64($file, function (base64Data) {
                        $scope.$apply(function () {
                            vm.listeArticles[fieldName] = base64Data;
                            vm.listeArticles[fieldName + 'ContentType'] = $file.type;
                        });
                    });
                }
            };
            
            vm.zoomColumn = function (lookupCtrl,lookupTemplate, fieldname, entity) {
                $uibModal.open({
                    templateUrl: 'tpl/entities/'+lookupTemplate+'/'+lookupTemplate+'-dialog.html',
                    controller: lookupCtrl+'DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return entity;
                        }
                    }
                }).result.then(function(item) {
                        vm.listeArticles[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();
