/**
 * today : YYYY,MM,DD 오늘 날짜
 * terms : 약관종류,유효기간
 * privacies : privacies[i] : i+1번 개인정보의 수집일자와 약관종류
 * 이때 파기해야할 개인정보의 번호를 오름차순으로 return
 */


class Solution {
    fun addData(data:String, add:String):String{
        var answer = ""
        var dataList = data.split(".").toMutableList()
        var intAdd = add.toInt()
        var year = dataList[0].toInt()
        var month = dataList[1].toInt()
        var day = dataList[2].toInt()


        //달 초과
        month += intAdd
        if(month>12){
            year += month/12
            month %= 12
            if(month==0){
                month = 12
                year -= 1
            }
        }
        //day 는 하루 적게
        day = day-1
        if(day==0){
            day=28
            month -= 1
            if(month==0){
                month = 12
                year -= 1
            }
        }
        dataList[0] = StringBuilder().append(year).toString()
        dataList[1] = StringBuilder().append(month).toString()
        dataList[2] = StringBuilder().append(day).toString()
        if(month<10) dataList[1]= "0"+dataList[1]
        if(day<10) dataList[2] = "0"+dataList[2]

        answer = dataList.toString().replace(", ",".")
        answer = answer.replace("[","")
        answer = answer.replace("]","")
        return answer
    }
    fun isEnd(data: String, today: String):Boolean{
        //today 값이 작으면 false.
        var dataInt = data.replace(".","").toInt()
        var todayInt = today.replace(".","").toInt()
        if(dataInt>=todayInt) return false

        return true
    }
    fun solution(today: String, terms: Array<String>, privacies: Array<String>): IntArray {
        //각 해당되는 값들을 map으로 정리하자
        var answer = mutableListOf<Int>()
        var termsMap = mutableMapOf<String,String>()
        var privaciesList = mutableListOf<String>()
        var list =listOf<String>()

        for(i in terms.indices){
            list = terms[i].split(" ")
            termsMap.put(list[0],list[1])
        }

        for(i in privacies.indices){
            list = privacies[i].split(" ")
            privaciesList.add(addData(list[0],termsMap.getValue(list[1])))
            //add(addData(x,y)) : 해당되는 고객의 기간(x) 및 상품 종류(y)
        }
        //privaciesList 랑 today를 비교
        for(i in privaciesList.indices){
            if(isEnd(privaciesList[i],today)) answer.add(i+1)
        }
        return answer.toIntArray()
    }
}
fun main(){
    val a =Solution()
    //a.solution("2022.05.19", arrayOf("A 6", "B 12", "C 3"), arrayOf("2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"))
    //[1,3]
    //a.solution("2020.01.01", arrayOf("Z 3", "D 5"), arrayOf("2019.01.01 D", "2019.11.15 Z", "2019.08.02 D", "2019.07.01 D", "2018.12.28 Z"))
    //[1,4,5]
    //a.solution("2020.10.15", arrayOf("A 100"), arrayOf("2018.06.16 A", "2008.02.15 A"))
    //[2]
    a.solution("2009.12.31", arrayOf("A 13"), arrayOf("2008.11.03 A"))
    //[1]

}