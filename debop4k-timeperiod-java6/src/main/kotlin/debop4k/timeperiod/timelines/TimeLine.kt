/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package debop4k.timeperiod.timelines

import debop4k.timeperiod.*
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * TimePeriod의 컬렉션을 가지며, 이를 통해 여러 기간에 대한 Union, Intersection, Gap 등을 구할 수 있도록 합니다.
 *
 * @author sunghyouk.bae@gmail.com
 * @since 1.0
 */
open class TimeLine<T : ITimePeriod>(override val periods: ITimePeriodContainer,
                                     val _limits: ITimePeriod? = TimeRange.of(periods),
                                     override val mapper: ITimePeriodMapper? = null) : ITimeLine {
  override val limits: ITimePeriod
    get() = if (_limits != null) TimeRange(_limits) else TimeRange(periods)

  private val log = LoggerFactory.getLogger(TimeLine::class.java)

  override fun combinePeriods(): ITimePeriodCollection {
    if (periods.isEmpty())
      return TimePeriodCollection()

    val moments = timeLineMoments()
    if (moments.isEmpty) {
      return TimePeriodCollection.of(TimeRange.of(periods))
    }
    return combinePeriods(moments)
  }

  override fun intersectPeriods(): ITimePeriodCollection {
    if (periods.isEmpty()) {
      return TimePeriodCollection()
    }

    val moments = timeLineMoments()
    if (moments.isEmpty) {
      return TimePeriodCollection.of(TimeRange.of(periods))
    }
    return intersectPeriods(moments)
  }

  override fun calculateCaps(): ITimePeriodCollection {
    val tpc = TimePeriodCollection()

    log.trace("periods={}, limits={}", periods, limits)

    periods.periods
        .filter { limits.intersectsWith(it) }
        .forEach { tpc.add(TimeRange(it)) }

    val moments = timeLineMoments()
    if (moments.isEmpty) {
      return TimePeriodCollection.of(limits)
    }
    val range = TimeRange.of(mapPeriodStart(limits.start),
                             mapPeriodEnd(limits.end))
    return calculateGap(moments, range)
  }

  private fun timeLineMoments(): ITimeLineMomentCollection {
    return timeLineMoments(periods)
  }

  private fun timeLineMoments(periods: Collection<ITimePeriod>): ITimeLineMomentCollection {
    val moments = TimeLineMomentCollection()
    if (periods.isEmpty()) {
      return moments
    }

    // setup gap set with all start/end points
    val intersections = TimePeriodCollection()

    periods
        .filter { it.isMoment() }
        .forEach { p ->
          val intersection = limits.intersection(p)
          if (intersection != null && !intersection.isMoment()) {
            if (mapper != null) {
              intersection.setup(mapPeriodStart(intersection.start),
                                 mapPeriodEnd(intersection.end))
            }
            intersections += intersection
          }
        }

    moments.addAll(intersections)
    return moments
  }

  private fun mapPeriodStart(moment: DateTime): DateTime
      = mapper?.unmapStart(moment) ?: moment

  private fun mapPeriodEnd(moment: DateTime): DateTime
      = mapper?.unmapEnd(moment) ?: moment
}