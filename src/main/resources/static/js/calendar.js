/*
Copyright (c) 2020 by Paul Navasard (https://codepen.io/peanav/pen/ulkof)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files 
(the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, 
merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES 
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

import {commonAjax} from "./ajax.js";

function lcgRandColor(value) {
    var seed1 = lcgGenerator(value);
    var seed2 = lcgGenerator(seed1);
    var seed3 = lcgGenerator(seed2);

    seed1 = seed1 % 155;
    seed2 = seed2 % 155;
    seed3 = seed3 % 155;

    seed1 = (seed1 < 16 ? '0' : '') + seed1.toString(16);
    seed2 = (seed2 < 16 ? '0' : '') + seed2.toString(16);
    seed3 = (seed3 < 16 ? '0' : '') + seed3.toString(16);

    return seed1.toString(16) + seed2.toString(16) + seed3.toString(16);
}

function lcgGenerator(value) {
    return ((value * 279470273) % 16581375);
}


// TODO pack min 필요
! function() {

    var today = moment();

    function Calendar(selector) {
        this.el = document.querySelector(selector);
        this.current = moment().date(1);
        this.draw();
    }

    Calendar.prototype.draw = function() {
        var self = this;
        var year = self.current.year();
        var month = self.current.month() + 1;

        var ajax = new commonAjax;
        ajax.setURL("/event/monthly/" + year + "-" + month);
        ajax.setType("GET");
        ajax.setDataType("json");
        ajax.setOnSuccess(function(response) {
            if (Array.isArray(response)) {
                self.events = response;
                self.events.forEach(function(ev) {
                    ev.color = lcgRandColor(ev.event_idx);
                });
            } else {
                self.events = [];
            }
            self.drawHeader();
            self.drawMonth();

            var current = document.querySelector('.today');
            if (current) {
                window.setTimeout(function() {
                    self.openDay(current);
                }, 100);
            }
        });
        ajax.setOnError(function() {
            self.events = [];
            self.drawHeader();
            self.drawMonth();
        })
        ajax.ajax();

    }



    Calendar.prototype.drawHeader = function() {
        var self = this;
        if (!this.header) {
            //Create the header elements
            this.header = createElement('div', 'header');
            this.header.className = 'header';

            this.title = createElement('h1');
            this.title.className = "headerTitle";
            this.title.addEventListener("click", function() {
                $("#calendar .month").toggleClass("open");
            })

            var right = createElement('div', 'right');
            right.addEventListener('click', function() {
                self.nextMonth();
            });

            var left = createElement('div', 'left');
            left.addEventListener('click', function() {
                self.prevMonth();
            });

            //Append the Elements
            this.header.appendChild(this.title);
            this.header.appendChild(right);
            this.header.appendChild(left);
            this.el.appendChild(this.header);
        }

        this.title.innerHTML = this.current.format('MMMM YYYY');
    }

    Calendar.prototype.drawMonth = function() {
        var self = this;

        this.events.forEach(function(ev) {
            ev.date = self.current.clone().date(ev.date);
        });


        if (this.month) {
            this.oldMonth = this.month;
            this.oldMonth.className = 'month out ' + (self.next ? 'next' : 'prev');
            this.oldMonth.addEventListener('webkitAnimationEnd', function() {
                self.oldMonth.parentNode.removeChild(self.oldMonth);
                self.month = createElement('div', 'month');
                self.backFill();
                self.currentMonth();
                self.fowardFill();
                self.el.appendChild(self.month);
                window.setTimeout(function() {
                    self.month.className = 'month in ' + (self.next ? 'next' : 'prev');
                }, 100);
            });
        } else {
            this.month = createElement('div', 'month');
            this.el.appendChild(this.month);
            this.backFill();
            this.currentMonth();
            this.fowardFill();
            this.month.className = 'month open new';
        }
    }

    Calendar.prototype.backFill = function() {
        var clone = this.current.clone();
        var dayOfWeek = clone.day();

        if (!dayOfWeek) {
            return;
        }

        clone.subtract('days', dayOfWeek + 1);

        for (var i = dayOfWeek; i > 0; i--) {
            this.drawDay(clone.add('days', 1));
        }
    }

    Calendar.prototype.fowardFill = function() {
        var clone = this.current.clone().add('months', 1).subtract('days', 1);
        var dayOfWeek = clone.day();

        if (dayOfWeek === 6) {
            return;
        }

        for (var i = dayOfWeek; i < 6; i++) {
            this.drawDay(clone.add('days', 1));
        }
    }

    Calendar.prototype.currentMonth = function() {
        var clone = this.current.clone();

        while (clone.month() === this.current.month()) {
            this.drawDay(clone);
            clone.add('days', 1);
        }
    }

    Calendar.prototype.getWeek = function(day) {
        if (!this.week || day.day() === 0) {
            this.week = createElement('div', 'week');
            this.month.appendChild(this.week);
        }
    }

    Calendar.prototype.drawDay = function(day) {
        var self = this;
        this.getWeek(day);

        //Outer Day
        var outer = createElement('div', this.getDayClass(day));
        outer.addEventListener('click', function() {
            self.openDay(this);
        });

        //Day Name
        var name = createElement('div', 'day-name', day.format('ddd'));

        //Day Number
        var number = createElement('div', 'day-number', day.format('DD'));

        //Events
        var events = createElement('div', 'day-events');
        this.drawEvents(day, events);

        outer.appendChild(name);
        outer.appendChild(number);
        outer.appendChild(events);
        this.week.appendChild(outer);
    }

    Calendar.prototype.drawEvents = function(day, element) {
        if (day.month() === this.current.month()) {

            var todaysEvents = [];
            this.events.forEach(function(ev) {
                if (day.isSameOrAfter(ev.begin_date, 'day') && day.isSameOrBefore(ev.end_date, 'day')) {
                    todaysEvents.push(ev);
                }
            });

            todaysEvents.forEach(function(ev) {
                var evSpan = createElement('span', 'red');
                $(evSpan).css("background", "#" + ev.color);
                element.appendChild(evSpan);
            });
        }
    }

    Calendar.prototype.getDayClass = function(day) {
        var classes = ['day'];
        if (day.month() !== this.current.month()) {
            classes.push('other');
        } else if (today.isSame(day, 'day')) {
            classes.push('today');
        }
        return classes.join(' ');
    }

    Calendar.prototype.openDay = function(el) {
        var details, arrow;
        var dayNumber = +el.querySelectorAll('.day-number')[0].innerText || +el.querySelectorAll('.day-number')[0].textContent;
        var day = this.current.clone().date(dayNumber);

        var currentOpened = document.querySelector('.details');

        //Check to see if there is an open detais box on the current row
        if (currentOpened && currentOpened.parentNode === el.parentNode) {

            if (currentOpened.querySelector('.day-info').textContent == dayNumber) {
                currentOpened.addEventListener('webkitAnimationEnd', function() {
                    if (currentOpened.parentNode != null)
                        currentOpened.parentNode.removeChild(currentOpened);
                });
                currentOpened.addEventListener('oanimationend', function() {
                    if (currentOpened.parentNode != null)
                        currentOpened.parentNode.removeChild(currentOpened);
                });
                currentOpened.addEventListener('msAnimationEnd', function() {
                    if (currentOpened.parentNode != null)
                        currentOpened.parentNode.removeChild(currentOpened);
                });
                currentOpened.addEventListener('animationend', function() {
                    if (currentOpened.parentNode != null)
                        currentOpened.parentNode.removeChild(currentOpened);
                });
                currentOpened.className = 'details out';

                return;
            }

            details = currentOpened;
            currentOpened.querySelector('.day-info').innerText = dayNumber;
            arrow = document.querySelector("#calendar .arrow");
        } else {
            //Close the open events on differnt week row
            //currentOpened && currentOpened.parentNode.removeChild(currentOpened);
            if (currentOpened) {
                currentOpened.addEventListener('webkitAnimationEnd', function() {
                    if (currentOpened.parentNode != null)
                        currentOpened.parentNode.removeChild(currentOpened);
                });
                currentOpened.addEventListener('oanimationend', function() {
                    if (currentOpened.parentNode != null)
                        currentOpened.parentNode.removeChild(currentOpened);
                });
                currentOpened.addEventListener('msAnimationEnd', function() {
                    if (currentOpened.parentNode != null)
                        currentOpened.parentNode.removeChild(currentOpened);
                });
                currentOpened.addEventListener('animationend', function() {
                    if (currentOpened.parentNode != null)
                        currentOpened.parentNode.removeChild(currentOpened);
                });
                currentOpened.className = 'details out';
            }

            //Create the Details Container
            details = createElement('div', 'details in');
            var info = createElement('div', 'day-info', dayNumber);
            info.setAttribute('hidden', true);
            details.appendChild(info);

            //Create the arrow
            var arrow = createElement('div', 'arrow');

            //Create the event wrapper

            details.appendChild(arrow);
            el.parentNode.appendChild(details);
        }

        var todaysEvents = this.events.reduce(function(memo, ev) {
            if (day.isSameOrAfter(ev.begin_date, 'day') && day.isSameOrBefore(ev.end_date, 'day')) {
                memo.push(ev);
            }

            return memo;
        }, []);

        this.renderEvents(todaysEvents, details);

        $(arrow).css("left", el.offsetLeft - el.parentNode.offsetLeft + el.offsetWidth / 2 - 5);
    }

    Calendar.prototype.renderEvents = function(events, ele) {
        //Remove any events in the current details element
        var currentWrapper = ele.querySelector('.events');
        var wrapper = createElement('div', 'events in' + (currentWrapper ? ' new' : ''));

        events.forEach(function(ev) {
            var div = createElement('div', 'event');
            var square = createElement('div', 'event-category ' + 'red');
            var span = createElement('span', '', ev.title);

            if (ev.url) {
                $(span).click(function() {
                    window.open(ev.url, "_blank");
                })
            }

            $(square).css("background", "#" + ev.color);


            div.appendChild(square);
            div.appendChild(span);
            wrapper.appendChild(div);
        });

        if (!events.length) {
            var div = createElement('div', 'event empty');
            var span = createElement('span', '', '일정 없음');

            div.appendChild(span);
            wrapper.appendChild(div);
        }

        if (currentWrapper) {
            currentWrapper.className = 'events out';
            currentWrapper.addEventListener('webkitAnimationEnd', function() {
                currentWrapper.parentNode.removeChild(currentWrapper);
                ele.appendChild(wrapper);
            });
            currentWrapper.addEventListener('oanimationend', function() {
                currentWrapper.parentNode.removeChild(currentWrapper);
                ele.appendChild(wrapper);
            });
            currentWrapper.addEventListener('msAnimationEnd', function() {
                currentWrapper.parentNode.removeChild(currentWrapper);
                ele.appendChild(wrapper);
            });
            currentWrapper.addEventListener('animationend', function() {
                currentWrapper.parentNode.removeChild(currentWrapper);
                ele.appendChild(wrapper);
            });

            currentWrapper.parentNode.removeChild(currentWrapper);
            ele.appendChild(wrapper);
        } else {
            ele.appendChild(wrapper);
        }
    }

    Calendar.prototype.nextMonth = function() {
        this.current.add('months', 1);
        this.next = true;
        this.draw();
    }

    Calendar.prototype.prevMonth = function() {
        this.current.subtract('months', 1);
        this.next = false;
        this.draw();
    }

    window.Calendar = Calendar;

    function createElement(tagName, className, innerText) {
        var ele = document.createElement(tagName);
        if (className) {
            ele.className = className;
        }
        if (innerText) {
            ele.innderText = ele.textContent = innerText;
        }
        return ele;
    }
}();

! function() {

    var calendar = new Calendar('#calendar');

    // TOOD: 쓸까말까
    // $("#calendar").on("click", ".details.in", function() {
    //   $(this).toggleClass("full-height");
    // })

}();