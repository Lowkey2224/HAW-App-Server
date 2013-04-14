function Schnitzeljagd(data){
	var self=this;
	self.id=data.id;
	self.name=ko.observable(data.name);
	self.save=function(){
		$.ajax({
			type: "put",
			data: ko.toJSON(self),
			contentType: "application/json",
			url: "rest/schnitzeljagd/" + self.id,
			error: function(){
				alert("Could not save changes");
			}
		});
	};
	self.remove=function(){
		$.ajax({
			type: "delete",
			url: "rest/schnitzeljagd/" + self.id,
			success: function(){
				model.schnitzeljagden.remove(self);
			},
			error: function(){
				alert("Could not remove Schnitzeljagd");
			}
		});
	};
	
}

function AppViewModel() {
	var self=this;
    self.schnitzeljagden=ko.observableArray([]);
    
    self.remove=function(){alert("Currently not supported")};
    
    self.newSchnitzeljagdName=ko.observable();
    self.addSchnitzeljagd=function(){
    	$.ajax({
    		type: "post",
    		data: ko.toJSON({ name : self.newSchnitzeljagdName}),
    		contentType: "application/json",
    		url: "rest/schnitzeljagd",
    		success: function(data){
    			self.schnitzeljagden.push(new Schnitzeljagd(data));
    			self.newSchnitzeljagdName("");
    		},
    		error: function(){
    			alert("Could not add Schnitzeljagd");
    		}
    	})
    };
    
    $.ajax({
    	type: "get",
    	url: "rest/schnitzeljagd",
    	success: function(data){
    		var mappedSchnitzeljagden=$.map(data, function(item){
    			return new Schnitzeljagd(item);
    		});
    		self.schnitzeljagden(mappedSchnitzeljagden);
    	},
    	error: function(){
    		alert("Failed to load Schnitzeljadgen");
    	}
    });
}
var model=new AppViewModel();
ko.applyBindings(model);