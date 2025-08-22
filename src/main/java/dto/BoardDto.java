package dto;

public class BoardDto {
	
	private int bno;  // 새로운 글 게시판 번호
	private String bnum;   
	private String btitle;
	private String bcontent;
	private String memberid;
	private int bhit;
	private String bdate;
	
	private MemberDto memberDto ;
	private ReservationDto reservationDto;
	
	
	public BoardDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BoardDto(int bno, String bnum, String btitle, String bcontent, String memberid, int bhit, String bdate,
			MemberDto memberDto, ReservationDto reservationDto) {
		super();
		this.bno = bno;
		this.bnum = bnum;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.memberid = memberid;
		this.bhit = bhit;
		this.bdate = bdate;
		this.memberDto = memberDto;
		this.reservationDto = reservationDto;
	}


	public int getBno() {
		return bno;
	}


	public void setBno(int bno) {
		this.bno = bno;
	}


	public String getBnum() {
		return bnum;
	}


	public void setBnum(String bnum) {
		this.bnum = bnum;
	}


	public String getBtitle() {
		return btitle;
	}


	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}


	public String getBcontent() {
		return bcontent;
	}


	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}


	public String getMemberid() {
		return memberid;
	}


	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}


	public int getBhit() {
		return bhit;
	}


	public void setBhit(int bhit) {
		this.bhit = bhit;
	}


	public String getBdate() {
		return bdate;
	}


	public void setBdate(String bdate) {
		this.bdate = bdate;
	}


	public MemberDto getMemberDto() {
		return memberDto;
	}


	public void setMemberDto(MemberDto memberDto) {
		this.memberDto = memberDto;
	}


	public ReservationDto getReservationDto() {
		return reservationDto;
	}


	public void setReservationDto(ReservationDto reservationDto) {
		this.reservationDto = reservationDto;
	}
	
	
	
	
	
}