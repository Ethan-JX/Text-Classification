/**
 * @desc 字符串组装
 * @author lm
 * 
 */
function StringBuffer() {
	this.array = new Array();
}
StringBuffer.prototype.append = function(value) {
	this.array[this.array.length] = value;
	return this;
}
StringBuffer.prototype.toString = function() {
	var str = this.array.join("");
	return str;
}