(function($) {
	
	var screens = [];
	var screenWidth = null;
	$('div.screen').each(function(i) {
		var screen = $(this);
		screens.push(screen);
		if (!screenWidth) {
			screenWidth = screen.outerWidth();
		}
		screen.css('left', screenWidth*i);
	});
	
	var modalHeight;
	$('div.modal').each(function(i) {
		var modal = $(this);
		if (!modalHeight) {
			modalHeight = $(document).height();
		}
		modal.css('top', modalHeight+'px');
	});
	
	var ExpenseModel = Backbone.Model.extend({
	    urlRoot: '/cauldron-rest/api/expenses'
	});
	var ExpenseCollection = Backbone.Collection.extend({
	    model: ExpenseModel,
	    url: '/cauldron-rest/api/expenses'
	});
	
	var ExpenseView = Backbone.View.extend({
			el : $('#cauldron-app'),
			expenseTemplate : _.template( $('#expenseTemplate').html() ),
			actionTemplate : _.template( $('#actionTemplate').html() ),
			events : {
				'submit #add-transaction' : 'addExpense',
				'click button.delete' : 'removeExpense',
				'click #add-transaction' : 'showExpenseAddScreen',
				'click #edit-transaction' : 'showExpenseEditScreen',
				'click #list-transactions' : 'showExpenseListScreen',
				'click li.expense' : 'selectExpense'
			},

			initialize : function() {
				_.bindAll(this, 'render', 'showExpenseAddScreen', 'showExpenseListScreen', 'addExpense', 'removeExpense', 'selectExpense', 'updateForm', 'updateAction');

				var self = this;
				this.expenseCollection = new ExpenseCollection();
				this.expenseCollection.fetch({
					success : function(){
						self.render();
					}
				});
				this.expenseCollection.bind('remove', this.render);
				this.expenseCollection.bind('add', this.render);
				this.expenseCollection.bind('click', this.render);
				
				this.nullModel = new ExpenseModel();	
				this.selected = this.nullModel;
			},

			render : function() {
				this.updateAction();
				
				var expenseList = $('#expense-list', this.el);
				expenseList.empty();
				expenseList.append( this.expenseTemplate( { expenses: this.expenseCollection.toJSON() } ) );
				
				var expenseAction = $('#expense-action', this.el);
				expenseAction.empty();
				expenseAction.append( this.actionTemplate( { action: this.action } ) );
			},
			
			showExpenseListScreen : function() {
				$('div.screen', this.el).animate({left: '+='+screenWidth});
//				$('div.modal', this.el).animate({top: modalHeight+'px'});
			},
			
			showExpenseEditScreen : function() {
				$('div.screen', this.el).animate({left: '-='+screenWidth});
			},
			
			showExpenseAddScreen : function() {
				$('div.screen', this.el).animate({left: '-='+screenWidth});
//				$('div.modal', this.el).animate({top: '0'});
			},
			
			addExpense : function(e) {
	            e.preventDefault();
	            var expense = new ExpenseModel();
	            this.$el.find('input[name]').each(function() {
	            	expense.set(this.name, this.value);
	            	$(this).val('');
	            })
	            expense.save();
	            this.selected = expense;
	            this.expenseCollection.add(expense);
	            showExpenseListScreen();
			},
			
			removeExpense : function(e) {
				e.preventDefault();
//		        var id = $(e.currentTarget).data('id');
//		        var expense = this.expenseCollection.get(id);
//		        this.expenseCollection.remove(expense);
//		        expense.destroy();
				this.expenseCollection.remove(this.selected);
				this.selected.destroy();
				this.selected = null;
			},
			
			selectExpense : function(e) {
				e.preventDefault();
		        
		        this.selected.set({selected: false});
		        var id = $(e.currentTarget).data('id');
		        if (this.selected.get('id') == id) {
		        	this.selected = this.nullModel;
		        } else {
		        	this.selected = this.expenseCollection.get(id);
		        	console.log('selected expense: '+id);
		        }
		        this.selected.set({selected: true});
		        
		        this.updateForm();
		        this.render();
			},
			
			updateForm : function() {
				if (this.selected != this.nullModel) {
					var categories = _.reduce(this.selected.get('categories'), function(memo, category){ 
						return memo == null ? category.name : memo + ', ' + category.name; 
					}, null);
					
					$('#amount').val(this.selected.get('money').amount);
					$('input[name=type][value="expense"]').prop('checked', true);
					$('#date').val(this.selected.get('entryDate'));
					$('#categories').val(categories);
					$('#s2 > .stitle > h1', this.el).text('Edit expense');
				} else {
					$('#amount').val('amount');
					$('input[name=type][value="expense"]').prop('checked', false);
					$('#date').val('dd-MM-yyyy');
					$('#categories').val('');
					$('#s2 > .stitle > h1', this.el).text('Add expense');
				}
			},
			
			updateAction : function() {
				if (this.selected == this.nullModel) {
					this.action = { title: 'Expenses', id: 'add-transaction', name: 'add' };  
				} else {
					var title = this.selected.get('entryDate') + ' - ' + (this.selected.get('money').amount/100) + this.selected.get('money').currency;
					this.action = { title: title , id: 'edit-transaction', name: 'edit' };
				}
			}
	});

	var ExpenseView = new ExpenseView();
	
})(jQuery);