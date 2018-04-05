﻿/*
* jQuery jclock - Clock plugin - v 2.3.2
* http://plugins.jquery.com/project/jclock
*
* Copyright (c) 2007-2011 Doug Sparling <http://www.dougsparling.com>
* Licensed under the MIT License:
* http://www.opensource.org/licenses/mit-license.php
*/
(function($) { 
  $.fn.jclock = function(options) {
    var version = '2.3.2'; 
    var opts = $.extend({}, $.fn.jclock.defaults, options);         
    return this.each(function() {
      $this = $(this);
      $this.timerID = null;
      $this.running = false;       
      // Record keeping for seeded clock
      $this.increment = 0;
      $this.lastCalled = new Date().getTime(); 
      var o = $.meta ? $.extend({}, opts, $this.data()) : opts; 
      $this.format = o.format;
      $this.utc = o.utc;
      // deprecate utc_offset (v 2.2.0)
      $this.utcOffset = (o.utc_offset != null) ? o.utc_offset : o.utcOffset;
      $this.seedTime = o.seedTime;
      $this.timeout = o.timeout; 
      $this.css({
        fontFamily: o.fontFamily,
        fontSize: o.fontSize,
        backgroundColor: o.background,
        color: o.foreground
      }); 
      // %a
      $this.daysAbbrvNames = new Array(7);
      $this.daysAbbrvNames[0] = iKEPLang.clock.sDayName[0];
      $this.daysAbbrvNames[1] = iKEPLang.clock.sDayName[1];
      $this.daysAbbrvNames[2] = iKEPLang.clock.sDayName[2];
      $this.daysAbbrvNames[3] = iKEPLang.clock.sDayName[3];
      $this.daysAbbrvNames[4] = iKEPLang.clock.sDayName[4];
      $this.daysAbbrvNames[5] = iKEPLang.clock.sDayName[5];
      $this.daysAbbrvNames[6] = iKEPLang.clock.sDayName[6]; 
      // %A
      $this.daysFullNames = new Array(7);
      $this.daysFullNames[0] = iKEPLang.clock.fDayName[0];
      $this.daysFullNames[1] = iKEPLang.clock.fDayName[1];
      $this.daysFullNames[2] = iKEPLang.clock.fDayName[2];
      $this.daysFullNames[3] = iKEPLang.clock.fDayName[3];
      $this.daysFullNames[4] = iKEPLang.clock.fDayName[4];
      $this.daysFullNames[5] = iKEPLang.clock.fDayName[5];
      $this.daysFullNames[6] = iKEPLang.clock.fDayName[6]; 
      // %b
      $this.monthsAbbrvNames = new Array(12);
      $this.monthsAbbrvNames[0] = iKEPLang.clock.sMonthName[0];
      $this.monthsAbbrvNames[1] = iKEPLang.clock.sMonthName[1];
      $this.monthsAbbrvNames[2] = iKEPLang.clock.sMonthName[2];
      $this.monthsAbbrvNames[3] = iKEPLang.clock.sMonthName[3];
      $this.monthsAbbrvNames[4] = iKEPLang.clock.sMonthName[4];
      $this.monthsAbbrvNames[5] = iKEPLang.clock.sMonthName[5];
      $this.monthsAbbrvNames[6] = iKEPLang.clock.sMonthName[6];
      $this.monthsAbbrvNames[7] = iKEPLang.clock.sMonthName[7];
      $this.monthsAbbrvNames[8] = iKEPLang.clock.sMonthName[8];
      $this.monthsAbbrvNames[9] = iKEPLang.clock.sMonthName[9];
      $this.monthsAbbrvNames[10] = iKEPLang.clock.sMonthName[10];
      $this.monthsAbbrvNames[11] = iKEPLang.clock.sMonthName[11]; 
      // %B
      $this.monthsFullNames = new Array(12);
      $this.monthsFullNames[0] = iKEPLang.clock.fMonthName[0];
      $this.monthsFullNames[1] = iKEPLang.clock.fMonthName[1];
      $this.monthsFullNames[2] = iKEPLang.clock.fMonthName[2];
      $this.monthsFullNames[3] = iKEPLang.clock.fMonthName[3];
      $this.monthsFullNames[4] = iKEPLang.clock.fMonthName[4];
      $this.monthsFullNames[5] = iKEPLang.clock.fMonthName[5];
      $this.monthsFullNames[6] = iKEPLang.clock.fMonthName[6];
      $this.monthsFullNames[7] = iKEPLang.clock.fMonthName[7];
      $this.monthsFullNames[8] = iKEPLang.clock.fMonthName[8];
      $this.monthsFullNames[9] = iKEPLang.clock.fMonthName[9];
      $this.monthsFullNames[10] = iKEPLang.clock.fMonthName[10];
      $this.monthsFullNames[11] = iKEPLang.clock.fMonthName[11];
 
      $.fn.jclock.startClock($this); 
    });
  };       
  $.fn.jclock.startClock = function(el) {
    $.fn.jclock.stopClock(el);
    $.fn.jclock.displayTime(el);
  };
  $.fn.jclock.stopClock = function(el) {
    if(el.running) {
      clearTimeout(el.timerID);
    }
    el.running = false;
  };
  $.fn.jclock.displayTime = function(el) {
    var time = $.fn.jclock.currentTime(el);
    var formatted_time = $.fn.jclock.formatTime(time, el);
    el.attr('currentTime', time.getTime());
    el.html(formatted_time);
    el.timerID = setTimeout(function(){$.fn.jclock.displayTime(el)},el.timeout);
  };
  $.fn.jclock.currentTime = function(el) {
    if(typeof(el.seedTime) == 'undefined') {
      // Seed time not being used, use current time
      var now = new Date();
    } else {
      // Otherwise, use seed time with increment
      el.increment += new Date().getTime() - el.lastCalled;
      var now = new Date(el.seedTime + el.increment);
      el.lastCalled = new Date().getTime();
    } 
    if(el.utc == true) {
      var localTime = now.getTime();
      var localOffset = now.getTimezoneOffset() * 60000;
      var utc = localTime + localOffset;
      var utcTime = utc + (3600000 * el.utcOffset);
      var now = new Date(utcTime);
    }
    return now;
  };
  $.fn.jclock.formatTime = function(time, el) { 
    var timeNow = "";
    var i = 0;
    var index = 0;
    while ((index = el.format.indexOf("%", i)) != -1) {
      timeNow += el.format.substring(i, index);
      index++; 
      // modifier flag
      //switch (el.format.charAt(index++)) {
      //}      
      var property = $.fn.jclock.getProperty(time, el, el.format.charAt(index));
      index++;      
      //switch (switchCase) {
      //} 
      timeNow += property;
      i = index
    } 
    timeNow += el.format.substring(i);
    return timeNow;
  }; 
  $.fn.jclock.getProperty = function(dateObject, el, property) { 
    switch (property) {
      case "a": // abbrv day names
          return (el.daysAbbrvNames[dateObject.getDay()]);
      case "A": // full day names
          return (el.daysFullNames[dateObject.getDay()]);
      case "b": // abbrv month names
          return (el.monthsAbbrvNames[dateObject.getMonth()]);
      case "B": // full month names
          return (el.monthsFullNames[dateObject.getMonth()]);
      case "d": // day 01-31
          return dateObject.getDate();//((dateObject.getDate() < 10) ? "0" : "") + dateObject.getDate();
      case "H": // hour as a decimal number using a 24-hour clock (range 00 to 23)
          return ((dateObject.getHours() < 10) ? "0" : "") + dateObject.getHours();
      case "I": // hour as a decimal number using a 12-hour clock (range 01 to 12)
          var hours = (dateObject.getHours() % 12 || 12);
          return ((hours < 10) ? "0" : "") + hours;
      case "l": // hour as a decimal number using a 12-hour clock (range 1 to 12)
          var hours = (dateObject.getHours() % 12 || 12);
          //return ((hours < 10) ? "0" : "") + hours;
          return hours;
      case "m": // month number
          return dateObject.getMonth() + 1;//(((dateObject.getMonth() + 1) < 10) ? "0" : "") + (dateObject.getMonth() + 1);
      case "M": // minute as a decimal number
          return ((dateObject.getMinutes() < 10) ? "0" : "") + dateObject.getMinutes();
      case "p": // either `am' or `pm' according to the given time value,
          // or the corresponding strings for the current locale
    	  return (dateObject.getHours() < 12 ? iKEPLang.clock.am : iKEPLang.clock.pm);
      case "P": // either `AM' or `PM' according to the given time value,
    	  return (dateObject.getHours() < 12 ? iKEPLang.clock.AM : iKEPLang.clock.PM);
      case "S": // second as a decimal number
          return ((dateObject.getSeconds() < 10) ? "0" : "") + dateObject.getSeconds();
      case "y": // two-digit year
          return dateObject.getFullYear().toString().substring(2);
      case "Y": // full year
          return (dateObject.getFullYear());
      case "%":
          return "%";
    } 
  };    
  // plugin defaults (24-hour)
  $.fn.jclock.defaults = {
    format: '%H:%M:%S',
    utcOffset: 0,
    utc: false,
    fontFamily: '',
    fontSize: '35px',
    foreground: '',
    background: '',
    seedTime: undefined,
    timeout: 1000 // 1000 = one second, 60000 = one minute
  }; 
})(jQuery);