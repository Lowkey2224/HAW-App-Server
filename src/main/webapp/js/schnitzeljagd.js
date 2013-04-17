OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {
	defaultHandlerOptions : {
		'single' : true,
		'double' : false,
		'pixelTolerance' : 0,
		'stopSingle' : false,
		'stopDouble' : false
	},

	initialize : function(options) {
		this.handlerOptions = OpenLayers.Util.extend({},
				this.defaultHandlerOptions);
		OpenLayers.Control.prototype.initialize.apply(this, arguments);
		this.handler = new OpenLayers.Handler.Click(this, {
			'click' : this.trigger
		}, this.handlerOptions);
	},

	trigger : function(e) {
		var lonlat = map.getLonLatFromPixel(e.xy);
		model.newZielLongitude(lonlat.lon);
		model.newZielLatitude(lonlat.lat);
	}

});
var map, layer;
function initMap() {
	map = new OpenLayers.Map('map');
	layer = new OpenLayers.Layer.OSM("Simple OSM Map");
	map.addLayer(layer);
	map.setCenter(new OpenLayers.LonLat(-71.147, 42.472).transform(
			new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject()),
			12);
	var click = new OpenLayers.Control.Click();
	map.addControl(click);
	click.activate();
}

function Ziel(data) {
	var self = this;
	self.id = ko.observable(data.id);
	self.name = ko.observable(data.name);
	self.longitude = ko.observable(data.longitude);
	self.latitude = ko.observable(data.latitude);
	self.radius = ko.observable(data.radius);
}

function Schnitzeljagd(data) {
	var self = this;
	self.id = ko.observable(data.id);
	self.name = ko.observable(data.name);

	self.openAddZiel = function() {
		model.newZielSchnitzeljagdId(self.id());
		toggleModal();
	}
	self.save = function() {
		$.ajax({
			type : "put",
			data : ko.toJSON(self),
			contentType : "application/json",
			url : "rest/schnitzeljagd/" + self.id(),
			error : function() {
				alert("Could not save changes");
			}
		});
	};
	self.remove = function() {
		$.ajax({
			type : "delete",
			url : "rest/schnitzeljagd/" + self.id(),
			success : function() {
				model.schnitzeljagden.remove(self);
			},
			error : function() {
				alert("Could not remove Schnitzeljagd");
			}
		});
	};

	self.ziele = ko.observableArray([]);

	$.ajax({
		type : "get",
		url : "rest/ziel/all/" + self.id(),
		success : function(data) {
			var mappedZiele = $.map(data, function(item) {
				return new Ziel(item);
			});
			self.ziele(mappedZiele);
		},
		error : function() {
			alert("Failed to load Ziele");
		}
	});
}

function AppViewModel() {
	var self = this;
	self.schnitzeljagden = ko.observableArray([]);

	self.remove = function() {
		alert("Currently not supported")
	};

	self.newZielSchnitzeljagdId = ko.observable();
	self.newZielLongitude = ko.observable();
	self.newZielLatitude = ko.observable();
	self.newZielName = ko.observable();
	self.newZielRadius = ko.observable();

	self.newSchnitzeljagdName = ko.observable();

	self.addZiel = function() {
		$.ajax({
			type : "post",
			data : ko.toJSON({
				name : model.newZielName,
				longitude : model.newZielLongitude,
				latitude : model.newZielLatitude,
				radius : model.newZielRadius,
				schnitzeljagdId : model.newZielSchnitzeljagdId
			}),
			contentType : "application/json",
			url : "rest/ziel",
			success : function(data) {
				$.grep(self.schnitzeljagden(), function(e) {
					return e.id() == data.schnitzeljagdId.id
				})[0].ziele.push(new Ziel(data));
				self.newZielName("");
				toggleModal();
			},
			error : function() {
				alert("Could not add Ziel");
			}
		})
	};

	self.addSchnitzeljagd = function() {
		$.ajax({
			type : "post",
			data : ko.toJSON({
				name : self.newSchnitzeljagdName
			}),
			contentType : "application/json",
			url : "rest/schnitzeljagd",
			success : function(data) {
				self.schnitzeljagden.push(new Schnitzeljagd(data));
				self.newSchnitzeljagdName("");
			},
			error : function() {
				alert("Could not add Schnitzeljagd");
			}
		})
	};

	$.ajax({
		type : "get",
		url : "rest/schnitzeljagd",
		success : function(data) {
			var mappedSchnitzeljagden = $.map(data, function(item) {
				return new Schnitzeljagd(item);
			});
			self.schnitzeljagden(mappedSchnitzeljagden);
		},
		error : function() {
			alert("Failed to load Schnitzeljadgen");
		}
	});
}
var model = new AppViewModel();
ko.applyBindings(model);

function toggleModal() {
	$("#modal-background").toggleClass("active");
	$("#modal-content").toggleClass("active");
}

$(document).ready(function() {
	initMap();
	$("#modal-launcher, #modal-background, #modal-close").click(function() {
		toggleModal();
	});
});
