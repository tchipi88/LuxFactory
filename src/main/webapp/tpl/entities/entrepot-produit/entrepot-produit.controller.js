(function() {
    'use strict';

    angular
        .module('app')
        .controller('EntrepotProduitController', EntrepotProduitController);

    EntrepotProduitController.$inject = ['$state', 'DataUtils', 'EntrepotProduit', 'Activites', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','Entrepot','Produit','$filter'];

    function EntrepotProduitController($state, DataUtils, EntrepotProduit, Activites, ParseLinks, AlertService, paginationConstants, pagingParams,Entrepot,Produit,$filter) {

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
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.activitess = Activites.query();
        vm.produits = Produit.query();
        vm.entrepots = Entrepot.query();

        loadAll();

        function loadAll () {
                EntrepotProduit.query({
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
                vm.entrepotProduits = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        vm.search = search;

        function search()
        {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat);
            var selected_produit = vm.produit == null ? "" : vm.produit.denomination;
            var selected_entrepot = vm.entrepot == null ? "" : vm.entrepot.libelle;

            //alert ('article: '+ selected_activite +' dateDebut: ' +fromDate + ' datefin: '+ toDate);

            EntrepotProduit.query({
                page: vm.page - 1,
                size: 20,
                produit: selected_produit,
                entrepot: selected_entrepot,
                fromDate: fromDate, 
                toDate: toDate
            },  function (data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.entrepotProduits = data;
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
