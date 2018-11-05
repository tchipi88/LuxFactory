(function() {
    'use strict';

    angular
        .module('app')
        .controller('ProcesverbalController', ProcesverbalController);

    ProcesverbalController.$inject = ['$state', '$filter','DataUtils', 'Procesverbal',  'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','Client','Fournisseur'];

    function ProcesverbalController($state,$filter, DataUtils, Procesverbal,  ParseLinks, AlertService, paginationConstants, pagingParams,Client,Fournisseur) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.clear = clear;
        vm.loadAll = loadAll;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.clients = Client.query();
        vm.fournisseurs = Fournisseur.query();
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.search = search;

        loadAll();

        function loadAll () {
                Procesverbal.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.procesverbals = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function search(){
            var dateFormat = 'yyyy-MM-dd';
            var today = $filter('date')(new Date(), dateFormat);
            var fromDate = $filter('date')(vm.fromDate, dateFormat) == null? "1900-01-01": $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat)== null? "2100-12-31":$filter('date')(vm.toDate, dateFormat);
            var selected_client = vm.client == null ? "" : vm.client.nom;
            var selected_fournisseur = vm.fournisseur == null ? "" : vm.fournisseur.nom;

           
                    Procesverbal.query({
                    page: vm.page - 1,
                    size: vm.itemsPerPage,
                    client: selected_client,
                    fournisseur: selected_fournisseur,
                    fromDate: fromDate, 
                    toDate: toDate
                },  function (data, headers) {
                    vm.links = ParseLinks.parse(headers('link'));
                    vm.totalItems = headers('X-Total-Count');
                    vm.queryCount = vm.totalItems;
                    vm.procesverbals = data;
                });
            

            
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
            });
        }

       

        function clear() {
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.transition();
        }

        vm.datePickerOpenStatus.dateFin = false;
        vm.datePickerOpenStatus.dateDebut = false;
                
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
