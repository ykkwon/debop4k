/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package debop4k.timeperiod.timeranges

import debop4k.core.kodatimes.today
import debop4k.timeperiod.DefaultTimeCalendar
import debop4k.timeperiod.ITimeCalendar
import debop4k.timeperiod.models.Quarter
import debop4k.timeperiod.utils.addQuarter
import debop4k.timeperiod.utils.startTimeOfQuarter
import org.joda.time.DateTime

/**
 * Created by debop
 */
open class QuarterRange @JvmOverloads constructor(moment: DateTime = today(),
                                                  calendar: ITimeCalendar = DefaultTimeCalendar)
: QuarterTimeRange(moment, 1, calendar) {

  @JvmOverloads
  constructor(year: Int, quarter: Quarter, calendar: ITimeCalendar = DefaultTimeCalendar)
  : this(startTimeOfQuarter(year, quarter), calendar)


  val year: Int get() = startYear
  val quarter: Quarter get() = quarterOfStart

  fun nextQuarter(): QuarterRange = addQuarters(1)
  fun prevQuarter(): QuarterRange = addQuarters(-1)

  fun addQuarters(quarters: Int): QuarterRange {
    val yq = addQuarter(year, quarter, quarters)
    return QuarterRange(yq.year, yq.quarter, calendar)
  }

}