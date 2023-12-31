## Оглавление
1. Введение
2. Техническое задание
3. Архитектура системы
4. Описание реализации
5. Тестирование
6. Заключение
7. Список литературы
8. Приложения


---
## Giới thiệu

**Tên dự án**: Minvest  
**Nhóm**: P33202  
**Thành viên**: Đinh Trường Lãm  
**Mã số sinh viên**: 291186  
**Email**: dinhtruonglam001@gmail.com  
**Người hướng dẫn**: Ключев Аркадий Олегович  
**Tổ chức**: Đại học ITMO  
**Năm học**: 2023 - 2024  


**Tóm tắt**: Dự án Minvest là một dự án về đầu tư chứng khoán, cho phép người dùng sử dụng tiền ảo để mua cổ phiếu với giá thật, app sẽ cung cấp cho người dùng các thông tin về cổ phiếu, và cho phép người dùng tạo 1 khoản đầu tư chứa nhiều cổ phiếu khác nhau, và theo dõi lợi nhuận của khoản đầu tư đó. Giá của cổ phiếu lấy từ nguồn API của Rapid API - Twelve Data. Twelve Data là nguồn cung cấp API về giá cổ phiếu, cung cấp nhiều sàn chứng khoán như NASDAQ, CXA, NYSE, ... và nhiều loại tiền ảo như Bitcoin, Ethereum, ... Nhưng chủ yếu tập trung vào NASDAQ.

**Mục tiêu**: Tạo ra 1 app cho phép người dùng đầu tư cổ phiếu với tiền ảo, và theo dõi lợi nhuận của khoản đầu tư đó, để luyện tập cho đầu tư thực tế - nơi mà người dùng có thể mất tiền thật nếu đầu tư không hiệu quả, và cũng để tăng sự hiểu biết về đầu tư chứng khoán của người dùng.



## Điều khoản tham chiếu
### Yêu cầu chức năng
1. Tìm kiếm cổ phiếu theo tên
   Người dùng có thể search tên 1 mã cổ phiếu, hoặc tên gọi của công ty, và nhận được kết quả trả về là 1 danh sách các công ty có tên hoặc mã cổ phiếu chứa các ký tự vừa nhập vào.
   
2. Sắp xếp cổ phiếu theo giá, theo tên, giảm dần hoặc tăng dần
   Người dùng có thể sắp xếp cổ phiếu theo giá, theo tên, giảm dần hoặc tăng dần, để dễ dàng tìm kiếm cổ phiếu mình muốn mua
3. Sắp xếp lợi nhuận lịch sử các giao dịch
    Người dùng có thể sắp xếp lịch sử các giao dịch theo lợi nhuận, giảm dần hoặc tăng dần, để dễ dàng theo dõi lịch sử giao dịch của mình
4. Xem thông tin về cổ phiếu
   Bằng cách chạm vào 1 công ty cụ thể, người dùng có thể xem giá của cổ phiếu ngay hiện tại theo thời gian thực.
5. Tạo, xoá 1 khoản đầu tư
   Người dùng có thể tạo hoặc xoá 1 khoản đầu tư, nếu tạo, người dùng sẽ chọn các cổ phiếu mà mình muốn đầu tư vào, và số lượng cổ phiếu mà mình muốn mua. Số lượng cổ phiếu mua là không giới hạn, và người dùng có thể mua cổ phiếu với số lượng nhỏ nhất là 1 cổ phiếu.
6. Xoá 1 giao dịch
    Người dùng có thể xoá 1 giao dịch trong lịch sử giao dịch của mình, và số lượng cổ phiếu cũng sẽ xoá trong khoản đầu tư tương ứng.
7. Xem lợi nhuận của khoản đầu tư
   Người dùng có thể xem lợi nhuận của khoản đầu tư của mình, và xem lợi nhuận của từng cổ phiếu trong khoản đầu tư đó bất kỳ lúc nào, lợi nhuận được tính theo chênh lệch giá hiện tại của cổ phiếu và giá khi mua cổ phiếu đó, nhân với số lượng cổ phiếu đó. Và được cập nhật theo thời gian thực.
8. Xem lợi nhuận của giao dịch
    Người dùng có thể xem lợi nhuận của giao dịch của mình, lợi nhuận được tính theo chênh lệch giá hiện tại của cổ phiếu và giá khi mua cổ phiếu đó, nhân với số lượng cổ phiếu đó. Và được cập nhật theo thời gian thực.

### Bố cục giao diện
<div style="display: flex; flex-wrap: wrap;">
    <figure style="display: inline-block; text-align: center;">
        <img src="image1.png" alt="Alt text" width="200"/>
        <figcaption><i>Image 1</i></figcaption>
    </figure>  
    <figure style="display: inline-block; text-align: center;">
        <img src="image2.png" alt="Alt text" width="200"/>
        <figcaption><i>Image 2</i></figcaption>
    </figure>  
    <figure style="display: inline-block; text-align: center;">
        <img src="image3.png" alt="Alt text" width="200"/>
        <figcaption><i>Image 3</i></figcaption>
    </figure>  
    <figure style="display: inline-block; text-align: center;">
        <img src="image4.png" alt="Alt text" width="200"/>
        <figcaption><i>Image 4</i></figcaption>
    </figure>  
    <figure style="display: inline-block; text-align: center;">
        <img src="image5.png" alt="Alt text" width="200"/>
        <figcaption><i>Image 5</i></figcaption>
    </figure>  
    <figure style="display: inline-block; text-align: center;">
        <img src="image6.png" alt="Alt text" width="200"/>
        <figcaption><i>Image 6</i></figcaption>
    </figure>  
    <figure style="display: inline-block; text-align: center;">
        <img src="image7.png" alt="Alt text" width="200"/>
        <figcaption><i>Image 7</i></figcaption>
    </figure>
</div>
**Hình 1** Trang chính của ứng dụng
Người dùng sẽ được thấy danh sách các cổ phiếu và công ty tương ứng, ở đây, người dụng có thể sắp xếp theo tên, giá, hoặc tìm kiếm theo tên công ty hoặc mã cổ phiếu.

**Hình 2** Tìm kiếm cổ phiếu
Người dùng có thể tìm kiếm cổ phiếu theo tên công ty hoặc mã cổ phiếu, và sẽ nhận được danh sách các công ty có tên hoặc mã cổ phiếu chứa các ký tự vừa nhập vào.

**Hình 3** giá cổ phiếu
Sau khi người dùng chọn 1 công ty, người dùng sẽ được xem giá cổ phiếu của công ty đó theo thời gian thực.

**Hình 4** lịch sử các khoản đầu tư
Người dùng có thể xem lịch sử các khoản đầu tư của mình, và xem lợi nhuận của từng khoản đầu tư đó. Mũi tên màu xanh hướng lên thể hiện lợi nhuận tăng, mũi tên màu đỏ hướng xuống thể hiện lợi nhuận giảm. Người dùng có thể sắp xếp lịch sử các khoản đầu tư theo lợi nhuận, giảm dần hoặc tăng dần. Hoặc có thể xoá luôn khoản đầu tư, có nút refresh để cập nhật lại lợi nhuận của khoản đầu tư.

**Hình 5** chi tiết khoản đầu tư
Người dùng có thể xem chi tiết của 1 khoản đầu tư, và xem lợi nhuận của từng cổ phiếu trong khoản đầu tư đó bất kỳ lúc nào, lợi nhuận được tính theo chênh lệch giá hiện tại của cổ phiếu và giá khi mua cổ phiếu đó, nhân với số lượng cổ phiếu đó. Và được cập nhật theo thời gian thực.

**Hình 6** lịch sử các giao dịch
Người dùng có thể xem lịch sử các giao dịch của mình, và xem lợi nhuận của từng giao dịch đó. Mũi tên màu xanh hướng lên thể hiện lợi nhuận tăng, mũi tên màu đỏ hướng xuống thể hiện lợi nhuận giảm. Người dùng có thể sắp xếp lịch sử các giao dịch theo lợi nhuận, giảm dần hoặc tăng dần. Hoặc có thể xoá luôn giao dịch, có nút refresh để cập nhật lại lợi nhuận của giao dịch.

**Hình 7** Mua cổ phiếu
Người dùng chọn số lượng cổ phiếu muốn mua, nhỏ nhất là 1 cổ phiếu, tổng chi phí sẽ được hiển thị, và người dùng có thể mua cổ phiếu bằng cách chạm vào nút mua. Nếu người dùng đổi ý thì có thể chạm vào nút hủy để hủy giao dịch, hoặc có thể chạm vào bất kỳ đâu để đóng cửa sổ mua cổ phiếu.

**Bộ chọn màu**

| Name of Color | RGB           | Color Sample |
| ------------- | ------------- | ------------ |
| Green         | rgb(3, 255, 7)| <div style="width:20px;height:20px;background-color:rgb(3, 255, 7);"></div> |
| Dark Blue     | rgb(0, 102, 139)| <div style="width:20px;height:20px;background-color:rgb(0, 102, 139);"></div> |
| White         | rgb(252, 252, 255)| <div style="width:20px;height:20px;background-color:rgb(252, 252, 255);"></div> |
| Light Grey    | rgb(220, 227, 233)| <div style="width:20px;height:20px;background-color:rgb(220, 227, 233);"></div> |
| Red           | rgb(255, 0, 0)| <div style="width:20px;height:20px;background-color:rgb(255, 0, 0);"></div> |

## Kiến trúc hệ thống
![Alt text](architecture.png)
### Ngăn xếp công nghệ
Kotlin - ngôn ngữ lập trình chính
Android Studio - IDE
Retrofit - thư viện để gọi API
Room - thư viện để tương tác với cơ sở dữ liệu
Moshi - thư viện để chuyển đổi JSON sang object
Git - hệ thống quản lý phiên bản
Gradle - hệ thống quản lý phụ thuộc


### Các lớp của hệ thống
**Trải nghiệm người dùng và điều khiển**
1. Nhấp vào nút tìm kiếm
2. Nhấp vào nút sắp xếp, theo tên, lợi nhuận, giá, giảm dần hoặc tăng dần
3. Nhấp vào 1 công ty
4. Xem giá cổ phiếu của công ty đó
5. Xem danh mục các khoản đầu tư và dữ liệu của các khoản đầu tư đó
6. Xem lịch sử các giao dịch
7. Ấn vào nút tạo khoản đầu tư
8. Ấn vào nút mua cổ phiếu
9. Xoá 1 khoản đầu tư, xoá 1 giao dịch
   
**Tương tác với cơ sở dữ liệu**
1. Cập nhật dữ liệu về danh mục các khoản đầu tư, lịch sử các giao dịch, và lợi nhuận của các khoản đầu tư, lợi nhuận của các giao dịch
2. TÌm kiếm cổ phiếu theo tên công ty hoặc mã cổ phiếu, danh sách sẽ cập nhật ngay trong lúc người dùng nhập, không cần ấn nút enter hoặc nút tìm kiếm
3. Xoá 1 khoản đầu tư, xoá 1 giao dịch
4. Tạo 1 khoản đầu tư, mua cổ phiếu  

**Quy trình kinh doanh**
1. Cập nhật dữ liệu. 
2. Phân tích danh mục đầu tư, giao dịch của người dùng
3. Tổng hợp các thông tin về lợi nhuận của các khoản đầu tư, lợi nhuận của các giao dịch theo các tiêu chí khác nhau

### Yêu cầu bảo mật
Ứng dụng sẽ không thu thập bất kỳ dữ liệu nào của người dùng, và toàn bộ dữ liệu về các khoản đầu tư chỉ được lưu trữ trên thiết bị của người dùng và không được chia sẻ với bất kỳ ai khác.


### Cấu trúc cơ sở dữ liệu
**Inforlogical model**
![Alt text](inforlogicalmodel.png)

**Data model**
![Alt text](datamodel.png)

## Mô tả thực hiện

Trong dự án này, tôi dùng kiến trúc MVVM và Jetpack Compose để triển khai. 
+ Directory Composable: là nơi chứa các thành phần cho UI. Directory Data là nơi chứa các entity, các phương thức Dao, và Database.  
+ Directory Network: Là nơi chứa các phương thức để gọi API. 1 Class để chứa thông tin thu được từ Json khi gọi API, dùng thư viện Retrofit để truy vấn mạng và Moshi để chuyển đổi Json sang object.
+ file StockViewModel: Đây là viewmodel theo mô hình MVVM, nơi thao tác với các logic của ứng dụng, và gọi các phương thức để truy vấn cơ sở dữ liệu, và gọi API.
+ Directory res: Là nơi chứa toàn bộ resources cho ứng dụng, bao gồm các hình ảnh, các strings, color, style, ...
+ File sortBy: Đây là 1 sealed class, chứa các trường để sắp xếp các cổ phiếu theo tên, giá, lợi nhuận, giảm dần hoặc tăng dần.
+ Tệp android manifest: Đây là tệp quan trọng nhất của ứng dụng, chứa các thông tin về ứng dụng, như tên ứng dụng, phiên bản, các quyền truy cập(mạng, cơ sở dữ liệu, ...), các activity, các service, ...

## Kiểm thử
Ở đây, tôi dùng junit để kiểm thử, dùng để kiểm tra xem có gọi API chính xác không.
![Alt text](testing1.png)
![Alt text](testing2.png)

## Kết luận
Thông qua dự án này, tôi đã học được khá nhiều thứ, như kiến trúc MVVM, Clean Architecture, Jetpack Compose, Room, Retrofit, Moshi, Junit, ... và cũng đã có thể hoàn thành dự án. Tuy nhiên, vì thời gian có hạn, Nhiều chức năng vẫn còn dừng lại ở mức độ cơ bản. Dù vậy, tôi vẫn rất hài lòng với kết quả của dự án này, app đã hoạt động tốt, và có thể đáp ứng được các yêu cầu của người dùng. Tôi vẫn duy trì được thời gian để hoành thành dự án, trong 3 tháng. Mong sẽ có cơ hội được tiếp tục phát triển dự án này trong tương lai. Cảm ơn và xin chào.