

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="MemberCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Member Edit</h5>
                    
                    

	
    <form name="memberForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newMember()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['member','BB']" default="New member"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/member/list'"  title="${message(code: 'default.list.label',args:['Member'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Member']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="member.id"  ng-disabled="memberForm.$invalid" title="${message(code: 'default.save.label',args:['Member'], default: 'Save')}" ng-enabled="!memberForm.$invalid" data-ng-click="manualSaveMember()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Member']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="member.id"  ng-disabled="memberForm.$invalid" title="${message(code: 'default.update.label',args:['Member'], default: 'Update')}" ng-enabled="!memberForm.$invalid" data-ng-click="manualSaveMember()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Member']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="member.id"  title="${message(code: 'default.delete.label',args:['Member'], default: 'Delete')}" data-ng-click="confirmDeleteMember()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Member']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="address1">
                address1 
            </label>
            <div class="input-group"><input type="email" class="form-control" name="address1" required="required" data-ng-model='member.address1' />
		<div class="error" ng-show="memberForm.address1.$dirty && memberForm.address1.$invalid">
		<small class="error" ng-show="!memberForm.address1.$pristine && memberForm.address1.$invalid"><g:message code="default.invalid.label" args="['address1']" default=" Invalid address1 : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="address2">
                address2 
            </label>
            <div class="input-group"><input type="email" class="form-control" name="address2" required="required" data-ng-model='member.address2' />
		<div class="error" ng-show="memberForm.address2.$dirty && memberForm.address2.$invalid">
		<small class="error" ng-show="!memberForm.address2.$pristine && memberForm.address2.$invalid"><g:message code="default.invalid.label" args="['address2']" default=" Invalid address2 : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="city">
                city 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="city" ng-minlength="1" ng-maxlength="20" required="required" data-ng-model='member.city' />
		<div class="error" ng-show="memberForm.city.$dirty && memberForm.city.$invalid">
		<small class="error" ng-show="!memberForm.city.$pristine && memberForm.city.$invalid"><g:message code="default.invalid.label" args="['city']" default=" Invalid city : "/> <g:message code="default.minSize.label" args="['1']" default=" minSize: 1 "/>
<g:message code="default.maxSize.label" args="['20']" default=" maxSize: 20 "/>
</small>
		<small class="error" ng-show="memberForm.city.$error.minlength"><g:message code="default.short.label" args="['city']" default=" city too short "/></small>

		<small class="error" ng-show="memberForm.city.$error.maxlength"><g:message code="default.long.label" args="['city']" default=" city too long "/></small>
</div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="club">
                club 
            </label>
            <select id="club" name="club.id" data-ng-model='member.club' data-ng-controller="ClubCtrl" data-ng-init="getAllClub()" ng-options="c.id for c in clubs track by c.id" required="" class="many-to-one"/>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="company">
                company 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="company" ng-minlength="5" ng-maxlength="200" required="required" data-ng-model='member.company' />
		<div class="error" ng-show="memberForm.company.$dirty && memberForm.company.$invalid">
		<small class="error" ng-show="!memberForm.company.$pristine && memberForm.company.$invalid"><g:message code="default.invalid.label" args="['company']" default=" Invalid company : "/> <g:message code="default.minSize.label" args="['5']" default=" minSize: 5 "/>
<g:message code="default.maxSize.label" args="['200']" default=" maxSize: 200 "/>
</small>
		<small class="error" ng-show="memberForm.company.$error.minlength"><g:message code="default.short.label" args="['company']" default=" company too short "/></small>

		<small class="error" ng-show="memberForm.company.$error.maxlength"><g:message code="default.long.label" args="['company']" default=" company too long "/></small>
</div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="country">
                country 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="country" ng-minlength="1" ng-maxlength="20" required="required" data-ng-model='member.country' />
		<div class="error" ng-show="memberForm.country.$dirty && memberForm.country.$invalid">
		<small class="error" ng-show="!memberForm.country.$pristine && memberForm.country.$invalid"><g:message code="default.invalid.label" args="['country']" default=" Invalid country : "/> <g:message code="default.minSize.label" args="['1']" default=" minSize: 1 "/>
<g:message code="default.maxSize.label" args="['20']" default=" maxSize: 20 "/>
</small>
		<small class="error" ng-show="memberForm.country.$error.minlength"><g:message code="default.short.label" args="['country']" default=" country too short "/></small>

		<small class="error" ng-show="memberForm.country.$error.maxlength"><g:message code="default.long.label" args="['country']" default=" country too long "/></small>
</div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='member.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="email">
                email 
            </label>
            <div class="input-group"><input type="email" class="form-control" name="email" required="required" data-ng-model='member.email' />
		<div class="error" ng-show="memberForm.email.$dirty && memberForm.email.$invalid">
		<small class="error" ng-show="!memberForm.email.$pristine && memberForm.email.$invalid"><g:message code="default.invalid.label" args="['email']" default=" Invalid email : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="facebook">
                facebook 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="facebook" ng-minlength="5" ng-maxlength="200" required="required" data-ng-model='member.facebook' />
		<div class="error" ng-show="memberForm.facebook.$dirty && memberForm.facebook.$invalid">
		<small class="error" ng-show="!memberForm.facebook.$pristine && memberForm.facebook.$invalid"><g:message code="default.invalid.label" args="['facebook']" default=" Invalid facebook : "/> <g:message code="default.minSize.label" args="['5']" default=" minSize: 5 "/>
<g:message code="default.maxSize.label" args="['200']" default=" maxSize: 200 "/>
</small>
		<small class="error" ng-show="memberForm.facebook.$error.minlength"><g:message code="default.short.label" args="['facebook']" default=" facebook too short "/></small>

		<small class="error" ng-show="memberForm.facebook.$error.maxlength"><g:message code="default.long.label" args="['facebook']" default=" facebook too long "/></small>
</div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="firstname">
                firstname 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="firstname" ng-minlength="1" ng-maxlength="30" required="required" data-ng-model='member.firstname' />
		<div class="error" ng-show="memberForm.firstname.$dirty && memberForm.firstname.$invalid">
		<small class="error" ng-show="!memberForm.firstname.$pristine && memberForm.firstname.$invalid"><g:message code="default.invalid.label" args="['firstname']" default=" Invalid firstname : "/> <g:message code="default.minSize.label" args="['1']" default=" minSize: 1 "/>
<g:message code="default.maxSize.label" args="['30']" default=" maxSize: 30 "/>
</small>
		<small class="error" ng-show="memberForm.firstname.$error.minlength"><g:message code="default.short.label" args="['firstname']" default=" firstname too short "/></small>

		<small class="error" ng-show="memberForm.firstname.$error.maxlength"><g:message code="default.long.label" args="['firstname']" default=" firstname too long "/></small>
</div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='member.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastname">
                lastname 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="lastname" ng-minlength="1" ng-maxlength="30" required="required" data-ng-model='member.lastname' />
		<div class="error" ng-show="memberForm.lastname.$dirty && memberForm.lastname.$invalid">
		<small class="error" ng-show="!memberForm.lastname.$pristine && memberForm.lastname.$invalid"><g:message code="default.invalid.label" args="['lastname']" default=" Invalid lastname : "/> <g:message code="default.minSize.label" args="['1']" default=" minSize: 1 "/>
<g:message code="default.maxSize.label" args="['30']" default=" maxSize: 30 "/>
</small>
		<small class="error" ng-show="memberForm.lastname.$error.minlength"><g:message code="default.short.label" args="['lastname']" default=" lastname too short "/></small>

		<small class="error" ng-show="memberForm.lastname.$error.maxlength"><g:message code="default.long.label" args="['lastname']" default=" lastname too long "/></small>
</div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="linkedin">
                linkedin 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="linkedin" ng-minlength="5" ng-maxlength="200" required="required" data-ng-model='member.linkedin' />
		<div class="error" ng-show="memberForm.linkedin.$dirty && memberForm.linkedin.$invalid">
		<small class="error" ng-show="!memberForm.linkedin.$pristine && memberForm.linkedin.$invalid"><g:message code="default.invalid.label" args="['linkedin']" default=" Invalid linkedin : "/> <g:message code="default.minSize.label" args="['5']" default=" minSize: 5 "/>
<g:message code="default.maxSize.label" args="['200']" default=" maxSize: 200 "/>
</small>
		<small class="error" ng-show="memberForm.linkedin.$error.minlength"><g:message code="default.short.label" args="['linkedin']" default=" linkedin too short "/></small>

		<small class="error" ng-show="memberForm.linkedin.$error.maxlength"><g:message code="default.long.label" args="['linkedin']" default=" linkedin too long "/></small>
</div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="mobile">
                mobile 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="mobile" ng-minlength="6" ng-maxlength="15" required="required" data-ng-model='member.mobile' />
		<div class="error" ng-show="memberForm.mobile.$dirty && memberForm.mobile.$invalid">
		<small class="error" ng-show="!memberForm.mobile.$pristine && memberForm.mobile.$invalid"><g:message code="default.invalid.label" args="['mobile']" default=" Invalid mobile : "/> <g:message code="default.minSize.label" args="['6']" default=" minSize: 6 "/>
<g:message code="default.maxSize.label" args="['15']" default=" maxSize: 15 "/>
</small>
		<small class="error" ng-show="memberForm.mobile.$error.minlength"><g:message code="default.short.label" args="['mobile']" default=" mobile too short "/></small>

		<small class="error" ng-show="memberForm.mobile.$error.maxlength"><g:message code="default.long.label" args="['mobile']" default=" mobile too long "/></small>
</div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="passwordHash">
                passwordHash 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="passwordHash" required="required" data-ng-model='member.passwordHash' />
		<div class="error" ng-show="memberForm.passwordHash.$dirty && memberForm.passwordHash.$invalid">
		<small class="error" ng-show="!memberForm.passwordHash.$pristine && memberForm.passwordHash.$invalid"><g:message code="default.invalid.label" args="['passwordHash']" default=" Invalid passwordHash : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="phone">
                phone 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="phone" ng-minlength="6" ng-maxlength="15" required="required" data-ng-model='member.phone' />
		<div class="error" ng-show="memberForm.phone.$dirty && memberForm.phone.$invalid">
		<small class="error" ng-show="!memberForm.phone.$pristine && memberForm.phone.$invalid"><g:message code="default.invalid.label" args="['phone']" default=" Invalid phone : "/> <g:message code="default.minSize.label" args="['6']" default=" minSize: 6 "/>
<g:message code="default.maxSize.label" args="['15']" default=" maxSize: 15 "/>
</small>
		<small class="error" ng-show="memberForm.phone.$error.minlength"><g:message code="default.short.label" args="['phone']" default=" phone too short "/></small>

		<small class="error" ng-show="memberForm.phone.$error.maxlength"><g:message code="default.long.label" args="['phone']" default=" phone too long "/></small>
</div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="state">
                state 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="state" ng-minlength="1" ng-maxlength="20" required="required" data-ng-model='member.state' />
		<div class="error" ng-show="memberForm.state.$dirty && memberForm.state.$invalid">
		<small class="error" ng-show="!memberForm.state.$pristine && memberForm.state.$invalid"><g:message code="default.invalid.label" args="['state']" default=" Invalid state : "/> <g:message code="default.minSize.label" args="['1']" default=" minSize: 1 "/>
<g:message code="default.maxSize.label" args="['20']" default=" maxSize: 20 "/>
</small>
		<small class="error" ng-show="memberForm.state.$error.minlength"><g:message code="default.short.label" args="['state']" default=" state too short "/></small>

		<small class="error" ng-show="memberForm.state.$error.maxlength"><g:message code="default.long.label" args="['state']" default=" state too long "/></small>
</div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="timezone">
                timezone 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="timezone" required="required" data-ng-model='member.timezone' />
		<div class="error" ng-show="memberForm.timezone.$dirty && memberForm.timezone.$invalid">
		<small class="error" ng-show="!memberForm.timezone.$pristine && memberForm.timezone.$invalid"><g:message code="default.invalid.label" args="['timezone']" default=" Invalid timezone : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="token">
                token 
            </label>
            <div class="input-group"><input class="form-control" name="token" type="number" data-ng-model='member.token'  required="required"/>
		<div class="error" ng-show="memberForm.token.$dirty && memberForm.token.$invalid">
		<small class="error" ng-show="!memberForm.token.$pristine && memberForm.token.$invalid"><g:message code="default.invalid.label" args="['token']" default=" Invalid token : "/> </small>
		<small class="error" ng-show="memberForm.token.$error.number"><g:message code="default.invalid.number.label" args="['token']" default=" Invalid token "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="twitter">
                twitter 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="twitter" ng-minlength="5" ng-maxlength="200" required="required" data-ng-model='member.twitter' />
		<div class="error" ng-show="memberForm.twitter.$dirty && memberForm.twitter.$invalid">
		<small class="error" ng-show="!memberForm.twitter.$pristine && memberForm.twitter.$invalid"><g:message code="default.invalid.label" args="['twitter']" default=" Invalid twitter : "/> <g:message code="default.minSize.label" args="['5']" default=" minSize: 5 "/>
<g:message code="default.maxSize.label" args="['200']" default=" maxSize: 200 "/>
</small>
		<small class="error" ng-show="memberForm.twitter.$error.minlength"><g:message code="default.short.label" args="['twitter']" default=" twitter too short "/></small>

		<small class="error" ng-show="memberForm.twitter.$error.maxlength"><g:message code="default.long.label" args="['twitter']" default=" twitter too long "/></small>
</div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="username">
                username 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="username" required="required" data-ng-model='member.username' />
		<div class="error" ng-show="memberForm.username.$dirty && memberForm.username.$invalid">
		<small class="error" ng-show="!memberForm.username.$pristine && memberForm.username.$invalid"><g:message code="default.invalid.label" args="['username']" default=" Invalid username : "/> </small></div></div>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
